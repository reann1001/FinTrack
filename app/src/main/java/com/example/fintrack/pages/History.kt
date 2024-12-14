package com.example.fintrack.pages

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
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
import com.example.fintrack.R
import com.example.fintrack.models.ExpenseModel
import com.example.fintrack.ui.theme.lightPurpleBackground
import com.example.fintrack.ui.theme.purpleBackground
import java.text.SimpleDateFormat
import java.util.*

class History (navController: NavController){

    @Composable
    fun HistoryView(expenseModel: ExpenseModel) {
        val currentDate = remember { Calendar.getInstance() }
        val monthYearFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
        val formattedDate = monthYearFormat.format(currentDate.time)

        var selectedDate by remember { mutableStateOf(formattedDate) }

        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.finalbg),
                contentDescription = "Background",
                modifier = Modifier.fillMaxSize()
            )

            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "Expense History",
                    fontFamily = FontFamily.Serif,
                    fontSize = 28.sp,
                    color = Color.White,
                    modifier = Modifier.padding(top = 50.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Display Date Picker UI
                ViewHistoryDate(
                    selectedDate = selectedDate,
                    onDateSelected = { selectedDate = it }
                )

                Spacer(modifier = Modifier.height(20.dp))

                ShowExpense(selectedDate, expenseModel)
            }
        }
    }

    @Composable
    fun ViewHistoryDate(selectedDate: String, onDateSelected: (String) -> Unit) {
        var expanded by remember { mutableStateOf(false) }

        val months = Array(12) { i ->
            SimpleDateFormat("MMMM yyyy", Locale.getDefault())
                .format(Calendar.getInstance().apply { set(Calendar.MONTH, i) }.time)
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF9127C3).copy(alpha = 0.2f))
                .border(1.dp, color = purpleBackground, shape = RoundedCornerShape(12.dp))
                .padding(16.dp)
                .height(25.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = selectedDate,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White,
                    modifier = Modifier.padding(start = 2.dp)
                )

                IconButton(
                    onClick = { expanded = !expanded },
                    modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Dropdown Icon",
                        tint = Color.Black
                    )
                }
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                months.forEach { month ->
                    DropdownMenuItem(
                        text = { Text(month) },
                        onClick = {
                            onDateSelected(month)
                            expanded = false
                        }
                    )
                }
            }
        }
    }

    @Composable
    fun ShowExpense(selectedDate: String, expenseModel: ExpenseModel) {
        val scannedProducts by expenseModel.scannedProducts.observeAsState(emptyList())

        val monthYearFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
        val filteredExpenses = scannedProducts.filter { product ->
            val expenseDate = monthYearFormat.format(product.date)
            expenseDate == selectedDate
        }

        val totalExpense = filteredExpenses.sumOf { it.price }

        Log.d("ShowExpenseList", "Recomposing ShowExpenseList for date: $selectedDate")

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(523.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(lightPurpleBackground)
                .border(1.dp, color = purpleBackground, RoundedCornerShape(16.dp))
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Product",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = purpleBackground
                    )
                    Text(
                        text = "Amount",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = purpleBackground
                    )
                }
                Divider(
                    color = purpleBackground,
                    thickness = 2.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )

                if (filteredExpenses.isEmpty()) {
                    Text(
                        text = "No products scanned for the selected month.",
                        color = Color.Gray,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                } else {
                    filteredExpenses.forEach { product ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = product.name,
                                color = purpleBackground,
                                modifier = Modifier.weight(1f),
                                fontSize = 14.sp
                            )

                            Text(
                                text = "₱${"%.2f".format(product.price)}",
                                color = purpleBackground,
                                fontSize = 14.sp
                            )
                        }
                        Divider(
                            color = purpleBackground,
                            thickness = 1.dp,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Total",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = "₱${"%.2f".format(totalExpense)}",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }
    }
}
