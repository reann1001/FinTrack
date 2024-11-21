package com.example.fintrack

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.fintrack.models.ExpenseModel
import com.example.fintrack.ui.theme.textColor

@Composable
fun ExpenseList(expenseModel: ExpenseModel) {
    // State to manage which product is being considered for deletion
    var productToDelete by remember { mutableStateOf<String?>(null) }

    // Fetch the list of scanned products from ExpenseModel
    val scannedProducts = expenseModel.scannedProducts.value

    // Log to check if the Composable is recomposing
    Log.d("ExpenseList", "Recomposing ExpenseList")

    // Box to hold the list
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(315.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFFEDE7F6))
            .border(1.dp, color = textColor, shape = RoundedCornerShape(16.dp))
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Column {
            // Display headers
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Product",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = "Amount",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
            Divider(color = Color.Black, thickness = 2.dp, modifier = Modifier.fillMaxWidth())

            // Check if the scannedProducts list is not empty
            if (scannedProducts.isEmpty()) {
                Text(
                    text = "No products scanned yet.",
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 16.dp)
                )
            } else {
                // Display each product with an amount and delete icon
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
                            color = Color.Black,
                            modifier = Modifier.weight(1f)
                        )

                        // Amount and delete icon
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "₱${"%.2f".format(product.price)}",
                                color = Color.Black,
                                fontSize = 14.sp
                            )
                            IconButton(
                                onClick = { productToDelete = product.barcode }, // Set the product to be deleted
                                modifier = Modifier.size(24.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete",
                                    tint = Color.Black
                                )
                            }
                        }
                    }
                    Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.fillMaxWidth())
                }
            }
        }
    }

    // Confirmation dialog
    if (productToDelete != null) {
        Dialog(onDismissRequest = { productToDelete = null }) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color(0xFF8E5FF1) // Set the background color here
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
                                productToDelete?.let { expenseModel.deleteProduct(it) }
                                productToDelete = null // Close the dialog
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
