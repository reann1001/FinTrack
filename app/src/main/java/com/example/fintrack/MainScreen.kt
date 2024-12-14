package com.example.fintrack

import android.provider.CalendarContract.Colors
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
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.ui.graphics.Color
import com.example.fintrack.models.BudgetModel
import com.example.fintrack.models.ExpenseModel
import com.example.fintrack.models.UserModel
import com.example.fintrack.pages.AddExpense
import com.example.fintrack.pages.EditProfile
import com.example.fintrack.pages.TrackExpense
import com.example.fintrack.ui.theme.accentColor
import com.example.fintrack.ui.theme.textColor

@Composable
fun MainScreen(navController: NavController, userModel: UserModel) {

    val navItemList = listOf(
        NavItem("Home", Icons.Default.Home),
        NavItem("Scanner", icon = ImageVector.vectorResource(id = R.drawable.ic_custom_scanner)),
        NavItem("History", icon = ImageVector.vectorResource(id = R.drawable.ic_custom_history)),
        NavItem("Profile", Icons.Default.Settings)
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
                        colors = NavigationBarItemColors(
                            selectedIconColor = Color(0xFFB89FFB),
                            selectedTextColor = Color(0xFFB89FFB),
                            selectedIndicatorColor = Color(0xFF9127C3),
                            unselectedIconColor = Color(0xFF757575),
                            unselectedTextColor = Color(0xFF757575),
                            disabledIconColor = Color(0xFFE1BEE7),
                            disabledTextColor = Color(0xFFE1BEE7)
                        ),
                        icon = {
                            Icon(
                                imageVector = navItem.icon,
                                contentDescription = "Icon",
                                modifier = Modifier.size(
                                    if (navItem.label == "History" || navItem.label == "Scanner")
                                        20.dp else 18.dp
                                ),
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
        ContentScreen(modifier = Modifier.padding(innerPadding),
            navController = navController,
            selectedIndex = selectedIndex,
            userModel = userModel,
            expenseModel = ExpenseModel()
        )
    }
}

@Composable
fun ContentScreen(modifier: Modifier = Modifier,
                  navController: NavController,
                  selectedIndex: Int,
                  userModel: UserModel,
                  expenseModel: ExpenseModel) {
    when (selectedIndex) {
        0 -> {
            val trackExpense = remember { TrackExpense(navController) }
            trackExpense.TrackExpenseView(navController)
        }
        1 -> {
            val addExpense = remember { AddExpense(navController) }
            addExpense.AddExpenseView()
        }
        2 -> {
            val history = remember { History(navController) }
            history.HistoryView(expenseModel)
        }
        3 -> {
            val editProfile = remember { EditProfile(navController, userModel) }
            editProfile.EditProfileView()
        }
    }
}