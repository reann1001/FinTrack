package com.example.fintrack.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fintrack.R

data class Expense(
    val productName: String,
    val amount: Double
)

data class ExpenseHistory(
    val date: String,
    val expenses: List<Expense>
)

val expenseHistories = listOf(
    ExpenseHistory("August 2024", listOf(Expense("Shampoo", 1000.0), Expense("Eggs", 2000.0), Expense("Cake", 2000.0))),
    ExpenseHistory("September 2024", listOf(Expense("Coffee", 1500.0), Expense("Transport", 500.0), Expense("Lunch", 800.0))),
    ExpenseHistory("October 2024", listOf(Expense("Books", 3000.0), Expense("Snacks", 200.0), Expense("Dinner", 1000.0))),
)

@Composable
fun History(navController: NavController) {
    val accentColor = Color(0xFF8E5FF1)
    val textColor = Color(0xFF6A2C91)
    val grayColor = Color(0xFFC0C0C0)

    Box(modifier = Modifier.fillMaxSize()) {
        // Background Image
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize()
        )

        // Content
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize().padding(top = 16.dp)
        ) {
            Text(
                text = "Expense History",
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold,
                color = textColor
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                items(expenseHistories) { history ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .background(Color(0xFF9768FA), shape = RoundedCornerShape(10.dp))
                            .clickable {
                                // Handle expense selection if needed
                                // Consider using remember to hold selected date and expenses if needed
                            }
                            .padding(12.dp)
                    ) {
                        Text(
                            text = history.date,
                            modifier = Modifier.fillMaxWidth(),
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center,
                            color = Color.White // Set text color to white for better visibility
                        )
                    }
                }
            }
        }
    }
}
