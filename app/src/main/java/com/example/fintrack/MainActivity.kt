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
import com.example.fintrack.ui.theme.AuthViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val authViewModel = AuthViewModel()
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "Login", builder = {
               composable("Signup",){
                    SignUp(navController, authViewModel)
                }

                composable("Login"){
                    LogIn(navController, authViewModel)
                }

                composable("SetBudget",){
                    SetBudget(navController, authViewModel)
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


