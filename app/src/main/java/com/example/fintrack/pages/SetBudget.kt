package com.example.fintrack.pages

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.fintrack.R
import com.example.fintrack.models.BudgetModel
import com.example.fintrack.ui.theme.accentColor
import com.example.fintrack.ui.theme.grayColor
import com.example.fintrack.ui.theme.textColor

class SetBudget(private val navController: NavController) {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun SetBudgetView(budgetModel: BudgetModel = viewModel()) {
        var initialBudget by remember { mutableStateOf("") }
        val context = LocalContext.current

        // Observe the save status and handle navigation after saving
        val saveStatus by budgetModel.budgetSaveStatus.observeAsState()
        var navigateToMainScreen by remember { mutableStateOf(false) }

        // Perform navigation if save operation was successful
        if (navigateToMainScreen) {
            LaunchedEffect(Unit) {
                navController.navigate("MainScreen")
                navigateToMainScreen = false
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            Image(
                painter = painterResource(id = R.drawable.fintracklogo),
                contentDescription = "FinTrack Logo",
                modifier = Modifier.size(250.dp)
            )

            Text(
                text = "Welcome to FinTrack!",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = textColor,
                fontFamily = FontFamily.Serif
            )
            Text(
                text = "Helping you stay on track.",
                color = grayColor
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = initialBudget,
                onValueChange = { initialBudget = it },
                label = { Text(text = "Enter Budget", color = grayColor) },
                modifier = Modifier.fillMaxWidth(0.8f),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = accentColor,
                    unfocusedBorderColor = accentColor,
                    cursorColor = accentColor
                ),
                textStyle = TextStyle(color = Color.Black)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    setBudget(
                        initialBudget,
                        budgetModel,
                        context,
                        onSuccess = { navigateToMainScreen = true })
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(top = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = accentColor)
            ) {
                Text(text = "Save", color = Color.White)
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "Skip",
                color = accentColor,
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable {
                        navController.navigate("MainScreen")
                    }
            )

            saveStatus?.let {
                when {
                    it.isSuccess -> {
                        Toast.makeText(context, it.getOrNull(), Toast.LENGTH_SHORT).show()
                    }

                    it.isFailure -> {
                        Toast.makeText(
                            context,
                            "Error: ${it.exceptionOrNull()?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun setBudget(
        initialBudget: String,
        budgetModel: BudgetModel,
        context: Context,
        onSuccess: () -> Unit
    ) {
        if (initialBudget.isNotBlank()) {
            try {
                val initialBudgetAmount = initialBudget.toDouble()

                budgetModel.saveOrUpdateBudget(initialBudgetAmount)

                onSuccess()
            } catch (e: NumberFormatException) {
                Toast.makeText(
                    context,
                    "Please enter a valid number for the budget.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            Toast.makeText(context, "Please enter a budget.", Toast.LENGTH_SHORT).show()
        }
    }
}
