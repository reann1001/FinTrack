package com.example.fintrack

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
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
import com.example.fintrack.pages.AddExpense
import com.example.fintrack.pages.History
import com.example.fintrack.pages.Homepage
import com.example.fintrack.pages.Profile
import com.example.fintrack.pages.ScanBarcode

@Composable
fun MainScreen(navController: NavController) {
    val navItemList = listOf(
        NavItem("Home", Icons.Default.Home),
        NavItem("History", icon = ImageVector.vectorResource(id = R.drawable.ic_custom_history)),
        NavItem("Add", Icons.Default.Add),
        NavItem("Scanner", icon = ImageVector.vectorResource(id = R.drawable.ic_custom_scanner)),
        NavItem("Profile", Icons.Default.AccountCircle)
    )

    var selectedIndex by remember {
        mutableIntStateOf(0)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
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
                                    )
                                )
                        },
                        label = {
                            Text(text = navItem.label)
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        ContentScreen(modifier = Modifier.padding(innerPadding), navController = navController, selectedIndex = selectedIndex) // Pass the selectedIndex and navController
    }
}

@Composable
fun ContentScreen(modifier: Modifier = Modifier, navController: NavController, selectedIndex: Int) { // Accept navController
    when (selectedIndex) {
        0 -> Homepage(navController) // Pass navController to each page
        1 -> History(navController)
        2 -> AddExpense(navController)
        3 -> ScanBarcode(navController)
        4 -> Profile(navController)
    }
}
