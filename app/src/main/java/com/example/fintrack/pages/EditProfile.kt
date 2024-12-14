package com.example.fintrack.pages

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fintrack.R
import com.example.fintrack.models.AuthState
import com.example.fintrack.models.UserModel
import com.example.fintrack.ui.theme.accentColor
import com.example.fintrack.ui.theme.grayColor
import com.example.fintrack.ui.theme.textColor

class EditProfile(private val navController: NavController, private val userModel: UserModel) {

    @Composable
    fun EditProfileView() {
        val authState by userModel.authState.observeAsState()

        // Redirect to login if unauthenticated
        LaunchedEffect(authState) {
            if (authState is AuthState.Unauthenticated) {
                navController.navigate("Login") {
                    popUpTo("Login") { inclusive = true }
                }
            }
        }

        // User data and context
        val context = LocalContext.current
        val email by remember { mutableStateOf(userModel.getCurrentUserEmail() ?: "Not Available") }
        var currentPassword by remember { mutableStateOf("") }
        var newPassword by remember { mutableStateOf("") }
        var confirmPassword by remember { mutableStateOf("") }

        Box(modifier = Modifier.fillMaxSize()) {
            // Background Image
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
                // Profile Title
                Text(
                    text = "Profile",
                    fontFamily = FontFamily.Serif,
                    fontSize = 28.sp,
                    color = Color.White,
                    modifier = Modifier.padding(top = 50.dp)
                )

                Spacer(modifier = Modifier.height(60.dp))

                // Profile Picture
                Box(
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF9127C3))
                        .border(1.dp, color = textColor, CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(80.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Display Email
                Text(
                    text = email,
                    fontFamily = FontFamily.Serif,
                    fontSize = 20.sp,
                    color = textColor
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Password Fields and Buttons
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Editable Password Fields
                    PasswordInputField("Current Password", currentPassword) { currentPassword = it }
                    PasswordInputField("New Password", newPassword) { newPassword = it }
                    PasswordInputField("Confirm Password", confirmPassword) { confirmPassword = it }

                    // Save Button
                    SaveButton {
                        when {
                            currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty() -> {
                                Toast.makeText(context, "All fields are required", Toast.LENGTH_SHORT).show()
                            }
                            newPassword != confirmPassword -> {
                                Toast.makeText(context, "New passwords do not match", Toast.LENGTH_SHORT).show()
                            }
                            else -> {
                                userModel.reauthenticateAndChangePassword(
                                    currentPassword, newPassword, context
                                ) { success ->
                                    if (success) {
                                        Toast.makeText(
                                            context,
                                            "Password updated successfully",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else {
                                        Toast.makeText(
                                            context,
                                            "Password update failed",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            }
                        }
                    }

                    // Logout Button
                    LogoutButton {
                        userModel.signout()
                        navController.navigate("Login") {
                            popUpTo("Login") { inclusive = true }
                        }
                    }
                }
            }
        }
    }

    // Password Input Field
    @Composable
    fun PasswordInputField(label: String, value: String, onValueChange: (String) -> Unit) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(text = label) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = textColor,
                unfocusedBorderColor = textColor,
                cursorColor = textColor,
                focusedLabelColor = grayColor,
                unfocusedLabelColor = grayColor
            ),
            textStyle = MaterialTheme.typography.bodyMedium.copy(color = Color.Black),
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(vertical = 2.dp)
        )
    }

    // Save Button
    @Composable
    fun SaveButton(onSaveClick: () -> Unit) {
        Button(
            onClick = onSaveClick,
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(vertical = 2.dp),
            colors = ButtonDefaults.buttonColors(containerColor = accentColor)
        ) {
            Text(text = "Save", color = Color.White)
        }
    }

    // Logout Button
    @Composable
    fun LogoutButton(onLogoutClick: () -> Unit) {
        TextButton(
            onClick = onLogoutClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Logout", color = Color.Red)
        }
    }
}