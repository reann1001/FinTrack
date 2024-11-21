package com.example.fintrack.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fintrack.ExpenseList
import com.example.fintrack.R
import com.example.fintrack.models.ExpenseModel
import com.example.fintrack.ui.theme.textColor
import java.text.SimpleDateFormat
import java.util.*

class History(private val navController: NavController) {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun HistoryView(expenseModel: ExpenseModel) {
        val currentDate = remember { Calendar.getInstance() }
        val monthYearFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
        val formattedDate = monthYearFormat.format(currentDate.time)

        var selectedDate by remember { mutableStateOf(formattedDate) }

        Box(modifier = Modifier.fillMaxSize()) {
            // Background Image
            Image(
                painter = painterResource(id = R.drawable.background),
                contentDescription = "Background",
                modifier = Modifier.fillMaxSize()
            )

            // Centered Column for the form content
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                // Expense History Title
                Text(
                    text = "Expense History",
                    fontFamily = FontFamily.Serif,
                    fontSize = 24.sp,
                    color = textColor,
                    modifier = Modifier.padding(top = 50.dp)
                )

                Spacer(modifier = Modifier.height(50.dp))

                // Display Date Picker UI
                viewHistoryDate(
                    selectedDate = selectedDate,
                    onDateSelected = { selectedDate = it }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Show Expense List based on the selected date
                showExpense(selectedDate, expenseModel)
            }
        }
    }

    // Function to show the Date Picker and handle date selection
    @Composable
    fun viewHistoryDate(selectedDate: String, onDateSelected: (String) -> Unit) {
        var expanded by remember { mutableStateOf(false) }
        val items = listOf("October 2024", "November 2024", "December 2024")

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFFEDE7F6))
                .border(1.dp, color = textColor, shape = RoundedCornerShape(16.dp))
                .padding(16.dp)
                .height(20.dp), // Reduced the height to 35dp
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Display selected date on the left
                Text(
                    text = selectedDate,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black,
                    modifier = Modifier.padding(start = 2.dp)
                )

                // Icon button for dropdown on the right
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

            // Dropdown menu positioned below the row
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                items.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(item) },
                        onClick = {
                            onDateSelected(item)
                            expanded = false
                        }
                    )
                }
            }
        }
    }

    // Function to show the expense list based on the selected date
    @Composable
    fun showExpense(selectedDate: String, expenseModel: ExpenseModel) {
        ExpenseList(expenseModel)
    }
}
