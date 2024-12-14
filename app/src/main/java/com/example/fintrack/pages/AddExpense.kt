package com.example.fintrack.pages

import android.Manifest
import android.util.Log
import android.util.Size
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.fintrack.models.BudgetModel
import com.example.fintrack.models.ExpenseModel
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.common.InputImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

class AddExpense(private val navController: NavController) {

    private val budgetModel = BudgetModel()
    private val expenseModel = ExpenseModel()

    @Composable
    fun AddExpenseView() {
        ScanBarcode(expenseModel, budgetModel)
    }

    @Composable
    fun ScanBarcode(expenseModel: ExpenseModel, budgetModel: BudgetModel) {
        val context = LocalContext.current
        val lifecycleOwner = LocalLifecycleOwner.current
        val cameraExecutor = remember { Executors.newSingleThreadExecutor() }

        // Camera permissions request
        val requestPermissionLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission()
        ) { granted ->
            if (!granted) {
                Toast.makeText(context, "Camera permission denied", Toast.LENGTH_SHORT).show()
            }
        }

        LaunchedEffect(Unit) {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }

        Box(modifier = Modifier.fillMaxSize()) {
            AndroidView(
                factory = { ctx ->
                    val previewView = PreviewView(ctx).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                    }

                    val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)
                    cameraProviderFuture.addListener({
                        val cameraProvider = cameraProviderFuture.get()

                        // Set up the preview
                        val preview = Preview.Builder().build().also {
                            it.setSurfaceProvider(previewView.surfaceProvider)
                        }

                        // Set up the barcode scanner
                        val barcodeAnalyzer = BarcodeAnalyzer(expenseModel, budgetModel, context)

                        val imageAnalysis = ImageAnalysis.Builder()
                            .setTargetResolution(Size(640, 480))
                            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                            .build().also {
                                it.setAnalyzer(cameraExecutor, barcodeAnalyzer)
                            }

                        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

                        try {
                            cameraProvider.unbindAll()
                            cameraProvider.bindToLifecycle(
                                lifecycleOwner, cameraSelector, preview, imageAnalysis
                            )
                            Log.d("CameraBinding", "Camera bound successfully")
                        } catch (exc: Exception) {
                            Log.e("CameraBinding", "Camera binding failed: ${exc.message}")
                            Toast.makeText(ctx, "Camera binding failed: ${exc.message}", Toast.LENGTH_SHORT).show()
                        }
                    }, ContextCompat.getMainExecutor(ctx))
                    previewView
                },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

class BarcodeAnalyzer(
    private val expenseModel: ExpenseModel,
    private val budgetModel: BudgetModel,
    private val context: android.content.Context
) : ImageAnalysis.Analyzer {

    private val scanner: BarcodeScanner = com.google.mlkit.vision.barcode.BarcodeScanning.getClient()
    private val processedBarcodes = mutableSetOf<String>()

    @androidx.annotation.OptIn(ExperimentalGetImage::class)
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
            scanner.process(image)
                .addOnSuccessListener { barcodes ->
                    for (barcode in barcodes) {
                        val barcodeValue = barcode.displayValue
                        if (!barcodeValue.isNullOrEmpty()) {
                            handleProductBarcode(barcodeValue)
                        }
                    }
                }
                .addOnFailureListener {
                    Log.e("BarcodeAnalyzer", "Barcode scanning failed: ${it.message}")
                }
                .addOnCompleteListener {
                    imageProxy.close()
                }
        } else {
            imageProxy.close()
        }
    }

    private fun handleProductBarcode(productBarcode: String) {
        Log.d("BarcodeAnalyzer", "Processing barcode: $productBarcode")
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val currentBudget = expenseModel.budget.value?.currentBudget ?: 0.0

                val message = when {
                    currentBudget <= 0 -> {
                        "Cannot add product. Budget is zero or less."
                    }
                    processedBarcodes.contains(productBarcode) -> {
                        Log.d("BarcodeAnalyzer", "Barcode already processed")
                        return@launch
                    }
                    else -> {
                        processedBarcodes.add(productBarcode)
                        val product = expenseModel.getProductByBarcode(productBarcode)

                        when {
                            product == null -> "Product not found."
                            currentBudget < product.price -> "Insufficient budget."
                            else -> {
                                budgetModel.addExpense(product.price)
                                "${product.name}' added successfully."
                            }
                        }
                    }
                }

                Log.d("BarcodeAnalyzer", "Displaying message: $message")
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

            } catch (e: Exception) {
                Log.e("BarcodeAnalyzer", "Error handling product barcode $productBarcode: ${e.message}", e)
                Toast.makeText(context, "Error fetching product", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
