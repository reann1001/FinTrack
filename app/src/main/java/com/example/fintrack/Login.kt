package com.example.fintrack

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fintrack.models.AuthState
import com.example.fintrack.models.UserModel
import com.example.fintrack.ui.theme.accentColor
import com.example.fintrack.ui.theme.grayColor
import com.example.fintrack.ui.theme.textColor

class Login(private val navController: NavController, private val userModel: UserModel) {

    @Composable
    fun LoginView() {
        val context = LocalContext.current
        val authState by userModel.authState.observeAsState(AuthState.Unauthenticated) // Default state

        // Handle the side effects based on authState
        AuthState(authState, navController, context)

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

            LoginInput(context)
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun LoginInput(context: Context) {
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "Email", color = grayColor) },
            modifier = Modifier.fillMaxWidth(0.8f),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = accentColor,
                unfocusedBorderColor = accentColor,
                cursorColor = accentColor
            ),
            textStyle = TextStyle(color = Color.Black)
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = "Password", color = grayColor) },
            modifier = Modifier.fillMaxWidth(0.8f),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = accentColor,
                unfocusedBorderColor = accentColor,
                cursorColor = accentColor
            ),
            textStyle = TextStyle(color = Color.Black)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { validateLoginInput(email, password, context) }, // Call validateLoginInput here
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

    private fun validateLoginInput(email: String, password: String, context: Context) {
        if (email.isNotBlank() && password.isNotBlank()) {
            userModel.login(email, password)
        } else {
            showError(context, "Email and password cannot be empty.")
        }
    }

    private fun showError(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    @Composable
    fun AuthState(authState: AuthState?, navController: NavController, context: Context) {
        LaunchedEffect(authState) {
            when (authState) {
                is AuthState.Authenticated -> {
                    navController.navigate("SetBudget") {
                        popUpTo("Login") { inclusive = true }
                    }
                }
                is AuthState.Error -> {
                    Toast.makeText(context, authState.message, Toast.LENGTH_SHORT).show()
                }
                else -> Unit
            }
        }
    }
}
