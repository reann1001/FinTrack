package com.example.fintrack.models

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ExpenseModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    val scannedProducts = mutableStateOf<List<Product>>(emptyList())

    init {
        fetchScannedProducts()
    }

    // Function to fetch scanned products from Firestore
    private fun fetchScannedProducts() {
        viewModelScope.launch {
            try {
                val result = db.collection("scannedProducts").get().await()
                val products = result.documents.mapNotNull { document ->
                    document.toObject(Product::class.java)
                }
                scannedProducts.value = products
            } catch (e: Exception) {
                Log.e("ExpenseModel", "Error fetching products: ${e.message}")
            }
        }
    }

    // Function to fetch product details by barcode and update scanned products list
    suspend fun getProductByBarcode(barcode: String): Product? {
        return try {
            val product = fetchProductFromFirestore(barcode)
            product?.let {
                // Add to the list if not already present
                if (scannedProducts.value.none { it.barcode == it.barcode }) {
                    scannedProducts.value = scannedProducts.value + it
                }
            }
            product
        } catch (e: Exception) {
            Log.e("ExpenseModel", "Error fetching product: ${e.message}")
            null
        }
    }

    // Fetch the product from Firestore using the barcode
    private suspend fun fetchProductFromFirestore(barcode: String): Product? {
        return try {
            val document = db.collection("products")
                .document(barcode)  // Using barcode as document ID
                .get()
                .await()

            if (!document.exists()) {
                Log.e("ExpenseModel", "Product not found: $barcode")
                null
            } else {
                val name = document.getString("name") ?: "Unknown"
                val price = document.getDouble("price") ?: 0.0
                val product = Product(barcode = barcode, name = name, price = price)

                // Store the product in the Firestore scannedProducts collection
                storeScannedProduct(product)

                product
            }
        } catch (e: Exception) {
            Log.e("ExpenseModel", "Error fetching product: ${e.message}")
            null
        }
    }

    // Function to store the scanned product into Firestore
    private suspend fun storeScannedProduct(product: Product) {
        try {
            val scannedProduct = mapOf(
                "barcode" to product.barcode,
                "name" to product.name,
                "price" to product.price,
                "scannedAt" to FieldValue.serverTimestamp()
            )

            // Store the product with barcode as document ID for uniqueness
            db.collection("scannedProducts")
                .document(product.barcode)  // Using barcode as document ID to avoid duplicates
                .set(scannedProduct)
                .await()

            Log.d("ExpenseModel", "Product added to scannedProducts: ${product.name}")
        } catch (e: Exception) {
            Log.e("ExpenseModel", "Error storing scanned product: ${e.message}")
        }
    }

    fun deleteProduct(barcode: String) {
        viewModelScope.launch {
            try {
                db.collection("scannedProducts")
                    .document(barcode)
                    .delete()
                    .await()

                // Update the local state
                scannedProducts.value = scannedProducts.value.filter { it.barcode != barcode }

                Log.d("ExpenseModel", "Product deleted: $barcode")
            } catch (e: Exception) {
                Log.e("ExpenseModel", "Error deleting product: ${e.message}")
            }
        }
    }
}

data class Product(
    val barcode: String = "",
    val name: String = "",
    val price: Double = 0.0
)
