import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.OptIn
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import co.nexlabs.betterhr.joblanding.android.R
import co.nexlabs.betterhr.joblanding.network.api.login.QRLogInViewModel
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

@Composable
fun QRScannerScreen(viewModel: QRLogInViewModel, navController: NavController) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var qrCode by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()
    val uiState by viewModel.uiState.collectAsState()

    if(uiState.isSuccessQRLogIn) {
        navController.popBackStack()
        Toast.makeText(context, "Web LogIn Successful!", Toast.LENGTH_LONG).show()
    }

    val cameraProviderFuture = remember {
        ProcessCameraProvider.getInstance(context)
    }

    val cameraPermissionGranted = remember {
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    if (!cameraPermissionGranted) {
        val permissionLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                // Camera permission granted
            } else {
                // Handle permission denied
            }
        }

        LaunchedEffect(Unit) {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                val previewView = PreviewView(context)
                val cameraProvider = cameraProviderFuture.get()

                val preview = Preview.Builder().build()
                preview.setSurfaceProvider(previewView.surfaceProvider)

                val barcodeScanner: BarcodeScanner = BarcodeScanning.getClient()
                val qrCodeAnalyzer = ImageAnalysis.Builder().build().also {
                    it.setAnalyzer(
                        Executors.newSingleThreadExecutor(),
                        QRCodeAnalyzer(barcodeScanner) { result ->
                            qrCode = result
                        })
                }

                val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

                try {
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        cameraSelector,
                        preview,
                        qrCodeAnalyzer
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                previewView
            }
        )


        if (qrCode != "") {
            scope.launch {
                viewModel.qrScanLogIn(qrCode)
               /* viewModel.validateQRToken(qrCode).subscribe(
                    {
                        //qrData -> viewModel.qrScanLogIn(qrCode)
                    },
                    { error ->
                        Toast.makeText(context, error.message, Toast.LENGTH_LONG).show()
                    }
                )*/
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Scanning ....",
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontWeight = FontWeight.W600,
                    color = Color(0xFFFFFFFF),
                    fontSize = 12.sp,
                )

                Image(
                    painter = painterResource(id = R.drawable.iv_scan_box),
                    contentDescription = "QR Logo",
                    modifier = Modifier
                        .size(200.dp)
                )

                Box(
                    modifier = Modifier
                        .clickable {
                            navController.popBackStack()
                        }
                        .height(40.dp)
                        .width(150.dp)
                        .border(1.dp, Color(0xFFFFFFFF), RoundedCornerShape(100.dp))
                        .background(color = Color.Transparent, shape = MaterialTheme.shapes.medium),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = "Cancel Scan",
                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                        fontWeight = FontWeight.W400,
                        color = Color(0xFFFFFFFF),
                        fontSize = 14.sp,
                    )
                }
            }
        }
    }
}

class QRCodeAnalyzer(
    private val barcodeScanner: BarcodeScanner,
    private val onQRCodeScanned: (String) -> Unit
) : ImageAnalysis.Analyzer {

    @OptIn(ExperimentalGetImage::class)
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
            barcodeScanner.process(image)
                .addOnSuccessListener { barcodes ->
                    for (barcode in barcodes) {
                        when (barcode.valueType) {
                            Barcode.TYPE_TEXT -> {
                                onQRCodeScanned(barcode.rawValue ?: "")
                            }
                        }
                    }
                }
                .addOnFailureListener {
                    // Handle any errors
                }
                .addOnCompleteListener {
                    imageProxy.close()
                }
        } else {
            imageProxy.close()
        }
    }
}
