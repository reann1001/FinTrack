package com.example.fintrack.pages

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.fintrack.R
import com.example.fintrack.models.BudgetModel
import com.example.fintrack.models.ExpenseModel
import com.example.fintrack.models.Product
import com.example.fintrack.ui.theme.accentColor
import com.example.fintrack.ui.theme.lightPurpleBackground
import com.example.fintrack.ui.theme.magenta
import com.example.fintrack.ui.theme.purpleBackground
import com.example.fintrack.ui.theme.textColor

class TrackExpense(private val navController: NavController) {

    @Composable
    fun TrackExpenseView(navController: NavController, expenseModel: ExpenseModel = viewModel()) {
        val budgetModel: BudgetModel = viewModel()

        val budget = budgetModel.budget.observeAsState().value

        // Format budget details
        val initialBudget = budget?.initialBudget?.let { "₱${"%.2f".format(it)}" } ?: "₱0.00"
        val currentBudget = budget?.currentBudget?.let { "₱${"%.2f".format(it)}" } ?: "₱0.00"
        val totalExpense = budget?.totalExpense?.let { "₱${"%.2f".format(it)}" } ?: "₱0.00"

        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.finalbg),
                contentDescription = "Background",
                modifier = Modifier.fillMaxSize()
            )
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(70.dp))
                Text(
                    text = "Welcome to FinTrack!",
                    fontSize = 25.sp,
                    color = Color.White,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(30.dp))
                DisplayBudget(initialBudget = initialBudget, currentBudget = currentBudget, totalExpense = totalExpense, navController = navController)
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Expenses:",
                    fontSize = 16.sp,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF6A2C91),
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                ExpenseList(expenseModel)
            }
        }
    }

    @Composable
    fun DisplayBudget(
        initialBudget: String,
        currentBudget: String,
        totalExpense: String,
        navController: NavController
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .shadow(elevation = 8.dp, shape = RoundedCornerShape(16.dp))
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            purpleBackground,
                            magenta
                        )
                    )
                )
                .padding(20.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Initial Budget",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontSize = 20.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Medium
                    ),
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = initialBudget,
                        style = MaterialTheme.typography.displaySmall.copy(
                            fontSize = 30.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            shadow = Shadow(
                                color = Color.Black.copy(alpha = 0.3f),
                                offset = Offset(4f, 4f),
                                blurRadius = 8f
                            )
                        )
                    )

                    IconButton(
                        onClick = { navController.navigate("SetBudget") },
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .size(24.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.edit_icon),
                            contentDescription = "Edit Initial Budget",
                            tint = Color(0xFFFAFAD2), // Pastel Yellow
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(25.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Current Budget",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontSize = 15.sp,
                                color = Color.White
                            )
                        )
                        Text(
                            text = currentBudget,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF32CD32),
                                shadow = Shadow(
                                    color = Color.Black.copy(alpha = 0.3f),
                                    offset = Offset(4f, 4f),
                                    blurRadius = 8f
                                )
                            )
                        )
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Total Expense",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontSize = 15.sp,
                                color = Color.White
                            )
                        )
                        Text(
                            text = totalExpense,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFFF6347),
                                shadow = Shadow(
                                    color = Color.Black.copy(alpha = 0.3f),
                                    offset = Offset(4f, 4f),
                                    blurRadius = 8f
                                )
                            )
                        )
                    }
                }
            }
        }
    }


    @Composable
    fun ExpenseList(expenseModel: ExpenseModel = viewModel()) {
        val scannedProducts by expenseModel.scannedProducts.observeAsState(emptyList())
        var productToDelete by remember { mutableStateOf<Product?>(null) }

        LaunchedEffect(scannedProducts) {
            Log.d("ExpenseList", "Scanned products updated. New size: ${scannedProducts.size}")
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(330.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(lightPurpleBackground)
                .border(1.dp, color = purpleBackground, RoundedCornerShape(16.dp))
                .padding(16.dp)
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
                        fontWeight = FontWeight.Bold,
                        color = purpleBackground
                    )
                    Text(
                        text = "Amount",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = purpleBackground
                    )
                }
                Divider(
                    color = purpleBackground,
                    thickness = 2.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                )

                if (scannedProducts.isNullOrEmpty()) {
                    Text(
                        text = "No products scanned yet.",
                        color = Color.Gray,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                } else {
                    scannedProducts.forEach { product ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            // Product name
                            Text(
                                text = product.name,
                                color = purpleBackground,
                                modifier = Modifier.weight(1f),
                                fontSize = 14.sp
                            )

                            Text(
                                text = "₱${"%.2f".format(product.price)}",
                                fontSize = 14.sp,
                                color = purpleBackground,
                                modifier = Modifier.padding(end = 8.dp)
                            )

                            IconButton(
                                onClick = {
                                    productToDelete = product
                                },
                                modifier = Modifier
                                    .size(24.dp)
                                    .padding(end = 2.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete",
                                    tint = purpleBackground
                                )
                            }
                        }
                        Divider(
                            color = purpleBackground,
                            thickness = 1.dp,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }


        if (productToDelete != null) {
            Dialog(onDismissRequest = { productToDelete = null }) {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = magenta
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Confirm Deletion",
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Are you sure you want to delete this product?",
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            horizontalArrangement = Arrangement.End,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            TextButton(onClick = { productToDelete = null }) {
                                Text(text = "No", color = Color.White)
                            }
                            TextButton(
                                onClick = {
                                    productToDelete?.let {
                                        expenseModel.deleteProduct(it.barcode)
                                    }
                                    productToDelete = null
                                }
                            ) {
                                Text(text = "Yes", color = Color.White)
                            }
                        }
                    }
                }
            }
        }
    }
}