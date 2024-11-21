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
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.fintrack.models.ExpenseModel
import com.example.fintrack.ui.theme.textColor
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.common.InputImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.Executors

class AddExpense(private val navController: NavController) {

    private val expenseModel = ExpenseModel()

    @Composable
    fun AddExpenseScreen() {
        // Calling ScanBarcode function to display the camera view
        ScanBarcode(expenseModel, navController)
    }

    @Composable
    fun ScanBarcode(expenseModel: ExpenseModel, navController: NavController) {
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
            // Camera Preview
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
                        val barcodeAnalyzer = BarcodeAnalyzer(expenseModel, context)

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

            // Overlay Text and Background
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "Scan Barcode",
                    fontFamily = FontFamily.Serif,
                    fontSize = 24.sp,
                    color = textColor,
                    modifier = Modifier.padding(top = 50.dp)
                )
            }
        }
    }
}

class BarcodeAnalyzer(
    private val expenseModel: ExpenseModel,
    private val context: android.content.Context
) : ImageAnalysis.Analyzer {

    private val scanner: BarcodeScanner = com.google.mlkit.vision.barcode.BarcodeScanning.getClient()

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

    private val processedBarcodes = mutableSetOf<String>()
    private fun handleProductBarcode(productBarcode: String) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                if (processedBarcodes.contains(productBarcode)) {
                    return@launch
                }

                processedBarcodes.add(productBarcode)

                val product = expenseModel.getProductByBarcode(productBarcode)

                val message = if (product != null) {
                    "${product.name}' added successfully"
                } else {
                    "Product not found"
                }

                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

            } catch (e: Exception) {
                Log.e("BarcodeAnalyzer", "Error handling product: ${e.message}", e)
                Toast.makeText(context, "Error fetching product", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
