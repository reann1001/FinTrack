package com.example.fintrack.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.fintrack.R

@Composable
fun AddExpense(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        //Background Image
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize()
        )
    }
}