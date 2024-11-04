package com.example.fintrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fintrack.Budget.SetBudget
import com.example.fintrack.pages.EditProfile
import com.example.fintrack.pages.Profile

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

                composable("Login"){
                    LogIn(navController)
                }

                composable("SetBudget",){
                    SetBudget(navController)
                }

                composable("MainScreen",){
                    MainScreen(navController)
                }

                composable("EditProfile",){
                    EditProfile(navController)
                }

                composable("Profile",){
                    Profile(navController)
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


