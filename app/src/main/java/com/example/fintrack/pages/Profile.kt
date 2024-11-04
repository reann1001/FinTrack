package com.example.fintrack.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.fintrack.ui.theme.textColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Profile(navController: NavController) {
    val name = "Rey Ann Golosino"
    val userName = "reann"
    val userEmail = "reyann.golosino@bisu.edu.ph"

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.background),
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
            Text(
                text = "Profile",
                fontFamily = FontFamily.Serif,
                fontSize = 24.sp,
                color = textColor,
                modifier = Modifier.padding(top = 50.dp)
            )

            Spacer(modifier = Modifier.height(100.dp))

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
                    // Name Section
                    Text(text = "Name", color = textColor, fontSize = 14.sp)
                    ProfileInfoCard(info = name)

                    // Username Section
                    Text(text = "Username", color = textColor, fontSize = 14.sp)
                    ProfileInfoCard(info = userName)

                    // Email Section
                    Text(text = "Email", color = textColor, fontSize = 14.sp)
                    ProfileInfoCard(info = userEmail)

                    Spacer(modifier = Modifier.height(20.dp))

                    // Profile Settings Button
                    Button(
                        onClick = {
                            // Navigate to profile settings screen or perform relevant action
                            navController.navigate("EditProfile")
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = accentColor)
                    ) {
                        Text(text = "Profile Settings", color = Color.White, fontSize = 16.sp)
                    }

                    // Logout Button
                    Button(
                        onClick = {
                            // Handle logout action here
                            navController.navigate("Login")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B0000))
                    ) {
                        Text(text = "Logout", color = Color.White, fontSize = 16.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileInfoCard(info: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, accentColor, RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp)),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Box(
            contentAlignment = Alignment.CenterStart,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = info, color = textColor, fontSize = 16.sp)
        }
    }
}
