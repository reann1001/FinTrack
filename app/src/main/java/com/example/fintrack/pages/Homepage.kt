package com.example.fintrack.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fintrack.R

@Composable
fun Homepage(navController: NavController) {

    val accentColor = Color(0xFF8E5FF1)
    val textColor = Color(0xFF6A2C91)
    val grayColor = Color(0xFFC0C0C0)

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Background Image
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize()
        )

        // Content of Homepage
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(65.dp))
            Text(
                text = "Welcome to FinTrack!",
                fontSize = 23.sp,
                color = Color(0xFF6A2C91)
            )

            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "@username",
                fontSize = 27.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(30.dp)) // Adjust spacer for positioning

            // Budget Box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .background(
                        Color(0xFF8E5FF1),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .clip(RoundedCornerShape(16.dp))
                    .padding(20.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Budget:",
                        fontSize = 18.sp,
                        color = Color.White,
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(bottom = 8.dp)
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "₱20,000.00",
                            fontSize = 35.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )

                        Spacer(modifier = Modifier.width(15.dp))
                        Text(
                            text = "Edit",
                            fontSize = 15.sp,
                            color = Color(0xFF6A2C91),
                            modifier = Modifier
                                .clickable { navController.navigate("SetBudget") }
                        )
                    }

                    Spacer(modifier = Modifier.height(25.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Current Budget:",
                            fontSize = 18.sp,
                            color = Color.White
                        )

                        Text(
                            text = "Total Expense:",
                            fontSize = 18.sp,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "₱15,000.00",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Green
                        )

                        Text(
                            text = "₱20,000.00",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Green
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Expense List
            Text(
                text = "Expenses:",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF6A2C91),
                modifier = Modifier.padding(bottom = 4.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .padding(8.dp) // Padding around the expense box to prevent overlap
                    .clip(RoundedCornerShape(16.dp)) // Rounded edges for the box
                    .background(Color(0xFFEDE7F6)) // Light background color
                    .border(1.dp, Color(0xFF6A2C91), RoundedCornerShape(16.dp)) // Border with rounded corners
                    .padding(16.dp) // Internal padding within the box
                    .verticalScroll(rememberScrollState())
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Product",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "Amount",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Divider(
                        color = Color.Black,
                        thickness = 2.dp,
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Additional rows for each expense item can be added here
                }
            }
        }
    }
}
