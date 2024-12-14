package com.example.fintrack.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.*

class ExpenseModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    private val _scannedProducts = MutableLiveData<List<Product>>()
    val scannedProducts: LiveData<List<Product>> get() = _scannedProducts

    private val _budget = MutableLiveData<Budget?>()
    val budget: LiveData<Budget?> get() = _budget

    init {
        fetchScannedProducts()
        fetchBudget()
    }

    private fun fetchScannedProducts() {
        db.collection("scannedProducts")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    return@addSnapshotListener
                }

                val products = snapshot?.documents?.mapNotNull { document ->
                    document.toObject(Product::class.java)?.copy(
                        date = document.getTimestamp("scannedAt")?.toDate() ?: Date()
                    )
                } ?: emptyList()
                _scannedProducts.postValue(products)
                updateBudget(products)
            }
    }

    private fun fetchBudget() {
        viewModelScope.launch {
            try {
                val result = db.collection("budgets")
                    .orderBy("initialBudget")
                    .limit(1)
                    .get()
                    .await()

                _budget.value = result.documents.firstOrNull()?.toObject(Budget::class.java)
            } catch (e: Exception) {
                // Handle exception
            }
        }
    }

    private fun updateBudget(products: List<Product>) {
        viewModelScope.launch {
            val totalExpense = products.sumOf { it.price }
            _budget.value?.let { currentBudget ->
                val updatedBudget = currentBudget.copy(
                    totalExpense = totalExpense,
                    currentBudget = currentBudget.initialBudget - totalExpense
                )
                saveBudget(updatedBudget)
                _budget.value = updatedBudget
            }
        }
    }

    private suspend fun saveBudget(budget: Budget) {
        try {
            val budgetRef = db.collection("budgets")
            val documents = budgetRef.get().await()

            if (documents.isEmpty) {
                budgetRef.document().set(budget).await()
            } else {
                val existingBudgetId = documents.first().id
                budgetRef.document(existingBudgetId).set(budget).await()
            }
        } catch (e: Exception) {
            // Handle exception
        }
    }

    suspend fun getProductByBarcode(barcode: String): Product? {
        return try {
            val currentBudget = _budget.value?.currentBudget ?: 0.0

            if (currentBudget <= 0) {
                return null
            }

            val product = fetchProductFromFirestore(barcode)
            product?.let {
                if (currentBudget < it.price) {
                    return null
                }

                val currentList = _scannedProducts.value.orEmpty()
                if (currentList.none { it.barcode == barcode }) {
                    _scannedProducts.value = currentList + it
                    updateBudget(_scannedProducts.value.orEmpty())
                    storeScannedProduct(it)
                }
            }
            product
        } catch (e: Exception) {
            null
        }
    }

    private suspend fun fetchProductFromFirestore(barcode: String): Product? {
        return try {
            val document = db.collection("products")
                .document(barcode)
                .get()
                .await()

            if (!document.exists()) {
                null
            } else {
                document.toObject(Product::class.java) ?: Product(
                    barcode = barcode,
                    name = document.getString("name") ?: "Unknown",
                    price = document.getDouble("price") ?: 0.0,
                    date = document.getTimestamp("scannedAt")?.toDate() ?: Date()
                )
            }
        } catch (e: Exception) {
            null
        }
    }

    private suspend fun storeScannedProduct(product: Product) {
        try {
            val scannedProduct = mapOf(
                "barcode" to product.barcode,
                "name" to product.name,
                "price" to product.price,
                "scannedAt" to FieldValue.serverTimestamp()
            )
            db.collection("scannedProducts")
                .document(product.barcode)
                .set(scannedProduct)
                .await()
        } catch (e: Exception) {
            // Handle exception
        }
    }

    fun deleteProduct(barcode: String) {
        viewModelScope.launch {
            try {
                val productToDelete = _scannedProducts.value?.find { it.barcode == barcode }

                if (productToDelete != null) {
                    db.collection("scannedProducts")
                        .document(barcode)
                        .delete()
                        .await()

                    _scannedProducts.value = _scannedProducts.value?.filter { it.barcode != barcode }

                    _budget.value?.let { currentBudget ->
                        val updatedBudget = currentBudget.copy(
                            totalExpense = (currentBudget.totalExpense - productToDelete.price).coerceAtLeast(0.0),
                            currentBudget = (currentBudget.currentBudget + productToDelete.price).coerceAtMost(currentBudget.initialBudget)
                        )
                        saveBudget(updatedBudget)
                        _budget.value = updatedBudget
                    }
                }
            } catch (e: Exception) {
                // Handle exception
            }
        }
    }
}

// Data model for Product
data class Product(
    val barcode: String = "",
    val name: String = "",
    val price: Double = 0.0,
    val date: Date = Date()
)
