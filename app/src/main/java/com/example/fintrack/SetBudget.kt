package com.example.fintrack.Budget

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fintrack.R
import com.example.fintrack.models.UserModel
import com.example.fintrack.ui.theme.accentColor
import com.example.fintrack.ui.theme.grayColor
import com.example.fintrack.ui.theme.textColor

class SetBudget(private val navController: NavController, authViewModel: UserModel) {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun SetBudgetView() {
        var budget by remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
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
                color = textColor,
                fontFamily = FontFamily.Serif
            )
            Text(
                text = "Helping you stay on track.",
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = budget,
                onValueChange = { budget = it },
                label = { Text(text = "Enter Budget", color = grayColor) },
                modifier = Modifier.fillMaxWidth(0.8f),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = accentColor,
                    unfocusedBorderColor = accentColor,
                    cursorColor = accentColor
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { setBudget(budget) },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(top = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = accentColor)
            ) {
                Text(text = "Save", color = Color.White)
            }

            Spacer(modifier = Modifier.weight(1f)) // Push content to the top

            // Clickable Skip text
            Text(
                text = "Skip",
                color = accentColor,
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable {
                        navController.navigate("MainScreen")
                    }
            )
        }
    }

    private fun setBudget(budget: String) {
        navController.navigate("MainScreen")
    }
}
