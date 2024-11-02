package com.example.fintrack

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fintrack.ui.theme.AuthViewModel
import com.example.fintrack.ui.theme.accentColor
import com.example.fintrack.ui.theme.grayColor
import com.example.fintrack.ui.theme.textColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetBudget(navController: NavController, authViewModel: AuthViewModel) {
    var budget by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), // Added padding for consistency
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Image(
            painter = painterResource(id = R.drawable.fintracklogo),
            contentDescription = "FinTrack Logo",
            modifier = Modifier.size(250.dp)
        )
        Text(
            text = "Welcome to FinTrack!",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = textColor
        )
        Text(
            text = "Help you to stay on Track.",
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = budget,
            onValueChange = { budget = it },
            label = { Text(text = "Enter Budget", color = grayColor) },
            modifier = Modifier.fillMaxWidth(0.8f),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = accentColor,
                unfocusedBorderColor = grayColor,
                cursorColor = accentColor,
                focusedLabelColor = accentColor,
                unfocusedLabelColor = grayColor
            )
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                // Handle save action, e.g., navigate to another screen or save the expense
                navController.navigate("MainScreen")
            },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(top = 16.dp), // Optional padding
            colors = ButtonDefaults.buttonColors(containerColor = accentColor)
        ) {
            Text(text = "Save", color = Color.White)
        }
    }
}
