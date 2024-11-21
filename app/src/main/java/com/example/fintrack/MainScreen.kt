package com.example.fintrack

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.fintrack.pages.History
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.Color
import com.example.fintrack.models.ExpenseModel
import com.example.fintrack.models.UserModel
import com.example.fintrack.pages.AddExpense
import com.example.fintrack.pages.TrackExpense
import com.example.fintrack.ui.theme.textColor

@Composable
fun MainScreen(navController: NavController, userModel: UserModel) {

    val navItemList = listOf(
        NavItem("Home", Icons.Default.Home),
        NavItem("History", icon = ImageVector.vectorResource(id = R.drawable.ic_custom_history)),
        NavItem("Scanner", icon = ImageVector.vectorResource(id = R.drawable.ic_custom_scanner)),
        NavItem("Settings", Icons.Default.Settings)
    )

    var selectedIndex by remember { mutableIntStateOf(0) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(
                containerColor = Color(0xFFEDE7F6)
            ) {
                navItemList.forEachIndexed { index, navItem ->
                    NavigationBarItem(
                        selected = selectedIndex == index,
                        onClick = {
                            selectedIndex = index
                        },
                        icon = {
                            Icon(
                                imageVector = navItem.icon,
                                contentDescription = "Icon",
                                modifier = Modifier.size(
                                    if (navItem.label == "History" || navItem.label == "Scanner")
                                        20.dp else 18.dp
                                ),
                                tint = if (selectedIndex == index) Color(0xFF9768FA) else Color.Black
                            )
                        },
                        label = {
                            Text(text = navItem.label,
                                color = textColor)
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        ContentScreen(modifier = Modifier.padding(innerPadding), navController = navController, selectedIndex = selectedIndex, userModel = userModel, expenseModel = ExpenseModel())
    }
}

@Composable
fun ContentScreen(modifier: Modifier = Modifier, navController: NavController, selectedIndex: Int, userModel: UserModel, expenseModel: ExpenseModel) { // Accept navController
    when (selectedIndex) {
        0 -> {
            val trackExpense = remember { TrackExpense(navController) }
            trackExpense.TrackExpenseView()
        }
        1 -> {
            val history = remember { History(navController) }
            history.HistoryView(expenseModel)
        }
        2 -> {
            val addExpense = remember { AddExpense(navController) }
            addExpense.AddExpenseScreen() // Call the barcode scanning function when "Scanner" is selected
        }
        3 -> {
            val editProfile = remember { EditProfile(navController, userModel) }
            editProfile.EditProfileView()
        }
    }
}