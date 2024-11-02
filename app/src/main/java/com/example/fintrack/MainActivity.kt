package com.example.fintrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "Login", builder = {
               composable("Signup",){
                    SignUp(navController)
                }

                composable("Login",){
                    LogIn(navController)
                }

                composable("SetBudget",){
                    SetBudget(navController)
                }

                composable("MainScreen",){
                    MainScreen(navController)
                }
/*
                composable("Notification",){
                    Notification(navController)
                }

                composable("History",){
                    History(navController)
                }

                composable("Scanner",){
                    Scanner(navController)
                }

                composable("Menu"){
                    Menu(navController)
                }

                composable("AddExpense"){
                    AddExpense(navController)
                }
*/
            } )
        }
    }
}


