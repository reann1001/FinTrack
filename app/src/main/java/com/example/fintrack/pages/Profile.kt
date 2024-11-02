package com.example.fintrack.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fintrack.R
import com.example.fintrack.ui.theme.accentColor
import com.example.fintrack.ui.theme.grayColor
import com.example.fintrack.ui.theme.textColor

@Composable
fun Profile(navController: NavController) {

    val userName = "John Doe"
    val userEmail = "johndoe@example.com"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        // Profile Picture
        Spacer(modifier = Modifier.height(50.dp))
        Box(
            modifier = Modifier
                .size(100.dp)
                .border(2.dp, accentColor, CircleShape)
                .padding(1.dp) // Padding around the image
        ) {
            Image(
                painter = painterResource(id = R.drawable.profile_icon),
                contentDescription = "Profile Picture",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        // User Name
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = userName,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = textColor
        )

        // User Email
        Text(
            text = userEmail,
            fontSize = 16.sp,
            color = grayColor
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Edit Profile Button
        Button(
            onClick = { /* Handle Edit Profile */ },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = accentColor)
        ) {
            Text(text = "Edit Profile", color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Logout Button
        TextButton(
            onClick = {navController.navigate("Login")},
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Logout", color = Color.Red)
        }
    }
}
