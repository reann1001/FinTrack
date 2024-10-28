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

@OptIn(ExperimentalMaterial3Api::class) // Opting into the experimental API
@Composable
fun LogIn(navController: NavController) {

    // Define color palette
    val accentColor = Color(0xFF8E5FF1)
    val textColor = Color(0xFF6A2C91)
    val grayColor = Color(0xFFC0C0C0)

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = R.drawable.fintracklogo),
            contentDescription = "FinTrack Logo",
            modifier = Modifier.size(300.dp)
        )

        Text(
            text = "LOGIN",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = textColor,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text(text = "Username", color = grayColor) },
            modifier = Modifier.fillMaxWidth(0.8f),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = accentColor,
                unfocusedBorderColor = grayColor,
                cursorColor = accentColor
            )
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = "Password", color = grayColor) },
            modifier = Modifier.fillMaxWidth(0.8f),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = accentColor,
                unfocusedBorderColor = grayColor,
                cursorColor = accentColor
            )
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.navigate("SetBudget") },
            colors = ButtonDefaults.buttonColors(containerColor = accentColor)
        ) {
            Text(text = "LOGIN", color = Color.White)
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Don't have an account?", color = grayColor)
        TextButton(onClick = { navController.navigate("Signup") }) {
            Text(text = "Signup", color = accentColor)
        }
    }
}
