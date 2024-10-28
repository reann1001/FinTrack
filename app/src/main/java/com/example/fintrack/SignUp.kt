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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUp(navController: NavController) {

    // Define color palette
    val accentColor = Color(0xFF8E5FF1)
    val textColor = Color(0xFF6A2C91)
    val grayColor = Color(0xFFC0C0C0)

    var name by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
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
            text = "Sign Up",
            fontSize = 35.sp,
            fontWeight = FontWeight.Bold,
            color = textColor,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Text(
            text = "Create an Account",
            color = grayColor,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text(text = "Name", color = grayColor) },
            modifier = Modifier.fillMaxWidth(0.8f),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = accentColor,
                unfocusedBorderColor = grayColor,
                cursorColor = accentColor
            )
        )
        Spacer(modifier = Modifier.height(8.dp))

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
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "Email", color = grayColor) },
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
            onClick = { navController.navigate("Login") },
            colors = ButtonDefaults.buttonColors(containerColor = accentColor)
        ) {
            Text(text = "SIGN UP", color = Color.White)
        }

        Text(text = "Already have an account?", color = grayColor)

        TextButton(onClick = { navController.navigate("Login") }) {
            Text(text = "Login", color = accentColor)
        }
    }
}
