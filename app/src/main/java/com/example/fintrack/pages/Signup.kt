package com.example.fintrack.pages

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
import com.example.fintrack.R
import com.example.fintrack.models.AuthState
import com.example.fintrack.models.UserModel
import com.example.fintrack.ui.theme.accentColor
import com.example.fintrack.ui.theme.grayColor
import com.example.fintrack.ui.theme.textColor

class Signup(private val navController: NavController, private val userModel: UserModel) {

    @Composable
    fun SignupView() {
        val context = LocalContext.current
        val authState by userModel.authState.observeAsState()

        // Handle authentication state
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
                modifier = Modifier.size(250.dp)
            )

            Text(
                text = "SIGN UP",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = textColor,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Text(
                text = "Create an Account",
                color = Color.Black,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            SignupInput(userModel, navController, context)
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun SignupInput(authViewModel: UserModel, navController: NavController, context: android.content.Context) {
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
            onClick = { validateSignUpInput(email, password, context, authViewModel) },
            colors = ButtonDefaults.buttonColors(containerColor = accentColor)
        ) {
            Text(text = "SIGN UP", color = Color.White)
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(text = "Already have an account?", color = grayColor)

        TextButton(onClick = { navController.navigate("Login") }) {
            Text(text = "Login", color = accentColor)
        }
    }

    private fun validateSignUpInput(email: String, password: String, context: android.content.Context, authViewModel: UserModel) {
        when {
            email.isEmpty() -> showError(context, "Email cannot be empty")
            password.isEmpty() -> showError(context, "Password cannot be empty")
            else -> authViewModel.signup(email, password)
        }
    }

    private fun showError(context: android.content.Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun AuthState(authState: AuthState?, navController: NavController, context: android.content.Context) {
    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Authenticated -> {
                navController.navigate("Login") {
                    popUpTo("Signup") { inclusive = true }
                }
            }
            is AuthState.Error -> {
                Toast.makeText(context, authState.message, Toast.LENGTH_SHORT).show()
            }
            else -> Unit
        }
    }
}