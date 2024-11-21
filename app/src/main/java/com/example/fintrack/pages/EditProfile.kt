package com.example.fintrack

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fintrack.models.AuthState
import com.example.fintrack.models.UserModel
import com.example.fintrack.ui.theme.accentColor
import com.example.fintrack.ui.theme.grayColor
import com.example.fintrack.ui.theme.textColor

class EditProfile(private val navController: NavController, private val userModel: UserModel) {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun EditProfileView() {
        // State for user input
        var email by remember { mutableStateOf("reyann.golosino@bisu.edu.ph") }
        var password by remember { mutableStateOf("***********") }

        val authState = userModel.authState.observeAsState()

        LaunchedEffect(authState.value) {
            when (authState.value) {
                is AuthState.Unauthenticated -> navController.navigate("Login")
                else -> Unit
            }
        }

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
                // Edit Profile Title at the Top
                Text(
                    text = "Settings",
                    fontFamily = FontFamily.Serif,
                    fontSize = 24.sp,
                    color = textColor,
                    modifier = Modifier.padding(top = 50.dp)
                )

                Spacer(modifier = Modifier.height(50.dp))

                // Box for Input Fields
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFFEDE7F6))
                        .padding(20.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        // Call separate functions for form fields and actions
                        editEmail(email, onEmailChange = { email = it })
                        editPassword(password, onPasswordChange = { password = it })
                        SaveButton { navController.navigate("MainScreen") }
                        LogoutButton { userModel.signout() }
                    }
                }
            }
        }
    }

    // Email input field function
    @Composable
    fun editEmail(email: String, onEmailChange: (String) -> Unit) {
        Text(text = "Email", color = textColor)
        OutlinedTextField(
            value = email,
            onValueChange = onEmailChange, // Update email as user types
            label = { Text(text = "Edit Email", color = grayColor) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = accentColor,
                unfocusedBorderColor = accentColor,
                cursorColor = accentColor,
                focusedLabelColor = grayColor,
                unfocusedLabelColor = grayColor
            ),
            textStyle = TextStyle(color = Color.Black),
            modifier = Modifier.fillMaxWidth()
        )
    }

    // Password input field function
    @Composable
    fun editPassword(password: String, onPasswordChange: (String) -> Unit) {
        Text(text = "Password", color = textColor)
        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChange, // Update password as user types
            label = { Text(text = "Edit Password", color = grayColor) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = accentColor,
                unfocusedBorderColor = accentColor,
                cursorColor = accentColor,
                focusedLabelColor = grayColor,
                unfocusedLabelColor = grayColor
            ),
            textStyle = TextStyle(color = Color.Black),
            modifier = Modifier.fillMaxWidth()
        )
    }

    // Save button function
    @Composable
    fun SaveButton(onSaveClick: () -> Unit) {
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = onSaveClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = accentColor)
        ) {
            Text(text = "Save", color = Color.White)
        }
    }

    // Logout button function
    @Composable
    fun LogoutButton(onLogoutClick: () -> Unit) {
        Spacer(modifier = Modifier.height(4.dp))
        TextButton(
            onClick = {
                onLogoutClick() // Call the signout function
                navController.navigate("Login") {
                    popUpTo("Login") { inclusive = true }
                    launchSingleTop = true
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Logout", color = Color.Red)
        }
    }
}
