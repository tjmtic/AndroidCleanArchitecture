package com.tiphubapps.ax.rain.presentation.screen.home


import android.util.Log
import android.util.Size
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.tiphubapps.ax.rain.presentation.components.ButtonComponent
import com.tiphubapps.ax.rain.util.QRCodeFoundListener
import com.tiphubapps.ax.rain.util.QRCodeImageAnalyzer
import kotlinx.coroutines.launch

@Composable
fun CameraPreview(
    modifier: Modifier = Modifier,
    scaleType: PreviewView.ScaleType = PreviewView.ScaleType.FILL_CENTER,
    cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA,
    onHideCamera: (String) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val lifecycleOwner = LocalLifecycleOwner.current
    //val c2 = LocalContext.current

    fun hideCamera(qrCode : String){
        onHideCamera(qrCode)
        Log.d("TIME123", "Logging for QR CODE:" + qrCode);
    }
    ButtonComponent(text = "Back", onClick = { hideCamera("none selected") }, enabled = true)
    AndroidView(
        modifier = modifier,
        factory = { context ->
            val previewView = PreviewView(context).apply {
                this.scaleType = scaleType
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            }

            // Preview
            val previewUseCase = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(previewView.surfaceProvider)
                }

            val imageAnalysis = ImageAnalysis.Builder()
                .setTargetResolution(Size(1280, 720))
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()

            imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(context), QRCodeImageAnalyzer(object :
                QRCodeFoundListener {
                override fun onQRCodeFound(_qrCode: String) {
                    //qrCode = _qrCode
                    //qrCodeFoundButton!!.visibility = View.VISIBLE
                    println("QR CODE!!:")
                    println(_qrCode);
                    hideCamera(_qrCode)
                }

                override fun qrCodeNotFound() {
                    //qrCodeFoundButton!!.visibility = View.INVISIBLE
                    println("no QR CODE!!:...")
                   // hideCamera()
                }
            }))

            coroutineScope.launch {
                val cameraProvider = context.getCameraProvider()
                try {
                    // Must unbind the use-cases before rebinding them.
                    cameraProvider.unbindAll()

                    cameraProvider.bindToLifecycle(
                        lifecycleOwner, cameraSelector, imageAnalysis, previewUseCase
                    )
                } catch (ex: Exception) {
                    Log.e("CameraPreview", "Use case binding failed", ex)
                }
            }

            previewView
        }
    )
}