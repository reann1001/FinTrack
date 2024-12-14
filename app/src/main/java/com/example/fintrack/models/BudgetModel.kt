package com.example.fintrack.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class BudgetModel : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()
    private val budgetRef = firestore.collection("budgets").document("defaultBudget")
    private val _budget = MutableLiveData<Budget?>()
    val budget: LiveData<Budget?> = _budget
    private val _budgetSaveStatus = MutableLiveData<Result<String>>()
    val budgetSaveStatus: LiveData<Result<String>> = _budgetSaveStatus

    init {
        observeBudget()
    }

    //Observe if naay changes sa firestore
    private fun observeBudget() {
        budgetRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                _budget.value = null
                return@addSnapshotListener
            }
            snapshot?.let {
                _budget.value = it.toObject(Budget::class.java)
            }
        }
    }

    fun saveOrUpdateBudget(newInitialBudget: Double) {
        viewModelScope.launch {
            try {
                val currentBudget = _budget.value
                val updatedBudget = currentBudget?.copy(
                    initialBudget = newInitialBudget,
                    currentBudget = (newInitialBudget - currentBudget.totalExpense).coerceAtLeast(0.0)
                ) ?: Budget(
                    initialBudget = newInitialBudget,
                    currentBudget = newInitialBudget,
                    totalExpense = 0.0
                )

                budgetRef.set(updatedBudget).await()
                _budget.value = updatedBudget
                _budgetSaveStatus.value = Result.success("Budget saved/updated successfully!")
            } catch (e: Exception) {
                _budgetSaveStatus.value = Result.failure(e)
            }
        }
    }

    fun addExpense(expenseAmount: Double) {
        viewModelScope.launch {
            try {
                val currentBudget = _budget.value
                if (currentBudget == null || currentBudget.currentBudget <= 0 || currentBudget.currentBudget < expenseAmount) {
                    return@launch
                }

                val updatedBudget = currentBudget.copy(
                    currentBudget = currentBudget.currentBudget - expenseAmount,
                    totalExpense = currentBudget.totalExpense + expenseAmount
                )

                budgetRef.update(
                    "currentBudget", updatedBudget.currentBudget,
                    "totalExpense", updatedBudget.totalExpense
                ).await()
                _budget.value = updatedBudget
            } catch (e: Exception) {
                // Handle exception
            }
        }
    }
}

data class Budget(
    val initialBudget: Double = 0.0,
    var currentBudget: Double = 0.0,
    var totalExpense: Double = 0.0
)
