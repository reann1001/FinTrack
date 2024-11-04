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
import androidx.compose.material3.TextButton
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
fun EditProfile(navController: NavController) {
    var name by remember { mutableStateOf("Rey Ann Golosino") }
    var username by remember { mutableStateOf("reann") }
    var email by remember { mutableStateOf("reyann.golosino@bisu.edu.ph") }
    var password by remember { mutableStateOf("***********") }

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
                text = "Edit Profile",
                fontFamily = FontFamily.Serif,
                fontSize = 24.sp,
                color = textColor,
                modifier = Modifier.padding(top = 50.dp)
            )

            // Add space below the title
            Spacer(modifier = Modifier.height(50.dp))

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
                    Text(text = "Name", color = textColor)

                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text(text = "Enter Name", color = grayColor) },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = accentColor,
                            unfocusedBorderColor = accentColor,
                            cursorColor = accentColor,
                            focusedLabelColor = grayColor,
                            unfocusedLabelColor = grayColor
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text(text = "Username", color = textColor)

                    OutlinedTextField(
                        value = username,
                        onValueChange = { username = it },
                        label = { Text(text = "Enter Username", color = grayColor) },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = accentColor,
                            unfocusedBorderColor = accentColor,
                            cursorColor = accentColor,
                            focusedLabelColor = grayColor,
                            unfocusedLabelColor = grayColor
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text(text = "Email", color = textColor)

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text(text = "Enter Email", color = grayColor) },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = accentColor,
                            unfocusedBorderColor = accentColor,
                            cursorColor = accentColor,
                            focusedLabelColor = grayColor,
                            unfocusedLabelColor = grayColor
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text(text = "Password", color = textColor)

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text(text = "Enter Password", color = grayColor) },
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
                            // Handle save action, e.g., navigate to another screen or save the profile
                            navController.navigate("Profile")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp), // Optional padding
                        colors = ButtonDefaults.buttonColors(containerColor = accentColor)
                    ) {
                        Text(text = "Save", color = Color.White)
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    TextButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Cancel", color = Color.Red)
                    }
                }
            }
        }
    }
}
