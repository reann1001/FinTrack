package com.example.fintrack.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fintrack.ExpenseList
import com.example.fintrack.R
import com.example.fintrack.models.ExpenseModel
import com.example.fintrack.ui.theme.accentColor
import com.example.fintrack.ui.theme.textColor

class TrackExpense (private val navController: NavController){

    @Composable
    fun TrackExpenseView() {
        val budget = "₱20,000.00"
        val currentBudget = "₱15,000.00"
        val totalExpense = "₱20,000.00"

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Background Image
            Image(
                painter = painterResource(id = R.drawable.background),
                contentDescription = "Background",
                modifier = Modifier.fillMaxSize()
            )

            // Content of Homepage
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp) // Consistent padding around the entire column
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(70.dp)) // Adjusted space for consistent header alignment

                // Welcome Text
                Text(
                    text = "Welcome to FinTrack!",
                    fontSize = 25.sp,
                    color = textColor,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(30.dp))

                // Budget Box
                DisplayBudget(budget, currentBudget, totalExpense, navController)

                Spacer(modifier = Modifier.height(20.dp))

                // Expense List Header
                Text(
                    text = "Expenses:",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor,
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                // Call the ExpenseList composable here
                ExpenseList(expenseModel = ExpenseModel())
            }
        }
    }

    @Composable
    fun DisplayBudget(budget: String, currentBudget: String, totalExpense: String, navController: NavController) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(accentColor)
                .padding(20.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Budget",
                    fontSize = 15.sp,
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(bottom = 8.dp)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = budget,
                        fontSize = 35.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.width(15.dp))
                    Text(
                        text = "Edit",
                        fontSize = 15.sp,
                        color = Color(0xFF6A2C91),
                        modifier = Modifier.clickable { navController.navigate("SetBudget") }
                    )
                }

                Spacer(modifier = Modifier.height(25.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Current Budget",
                        fontSize = 15.sp,
                        color = Color.White
                    )

                    Text(
                        text = "Total Expense",
                        fontSize = 15.sp,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = currentBudget,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Green
                    )

                    Text(
                        text = totalExpense,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Green
                    )
                }
            }
        }
    }
}
