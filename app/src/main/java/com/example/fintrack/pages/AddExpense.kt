package com.example.fintrack.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fintrack.R
import com.example.fintrack.ui.theme.accentColor
import com.example.fintrack.ui.theme.grayColor
import com.example.fintrack.ui.theme.textColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpense(navController: NavController) {
    var productName by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
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
            // Add Expense Title at the Top
            Text(
                text = "Add Expense",
                fontFamily = FontFamily.Serif, // Change to Roboto if added to resources
                fontSize = 24.sp,
                color = textColor, // Change color as needed
                modifier = Modifier.padding(top = 50.dp) // Add top padding
            )

            // Add space below the title
            Spacer(modifier = Modifier.height(100.dp))

            // Box for Input Fields
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFFEDE7F6)) // Set background with slight transparency
                    .padding(20.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(text = "Product", color = textColor)

                    OutlinedTextField(
                        value = productName,
                        onValueChange = { productName = it },
                        label = { Text(text = "Enter Product", color = grayColor) },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = accentColor,
                            unfocusedBorderColor = accentColor,
                            cursorColor = accentColor,
                            focusedLabelColor = grayColor,
                            unfocusedLabelColor = grayColor
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text(text = "Amount", color = textColor)

                    OutlinedTextField(
                        value = amount,
                        onValueChange = { amount = it },
                        label = { Text(text = "Enter Amount", color = grayColor) },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = accentColor,
                            unfocusedBorderColor = accentColor,
                            cursorColor = accentColor,
                            focusedLabelColor = grayColor,
                            unfocusedLabelColor = grayColor
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(10.dp)) // Space before the save button

                    Button(
                        onClick = {
                            // Handle save action, e.g., navigate to another screen or save the expense
                            navController.navigate("MainScreen")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp), // Optional padding
                        colors = ButtonDefaults.buttonColors(containerColor = accentColor)
                    ) {
                        Text(text = "Save", color = Color.White)
                    }
                }
            }
        }
    }
}
