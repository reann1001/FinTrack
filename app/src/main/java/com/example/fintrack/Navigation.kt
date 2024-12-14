package com.example.fintrack

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fintrack.models.BudgetModel
import com.example.fintrack.pages.SetBudget
import com.example.fintrack.models.UserModel
import com.example.fintrack.pages.Login
import com.example.fintrack.pages.Signup

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val userModel = UserModel()

    NavHost(navController = navController, startDestination = "Login") {
        composable("Signup") {
            val signup = Signup(navController, userModel)
            signup.SignupView()
        }

        composable("Login") {
            val login = Login(navController, userModel)
            login.LoginView()
        }

        composable("SetBudget") {
            val setBudget = SetBudget(navController)
            setBudget.SetBudgetView()
        }

        composable("MainScreen") {
            MainScreen(navController, userModel)
        }
    }
}
