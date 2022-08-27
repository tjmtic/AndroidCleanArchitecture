package com.farhan.tanvir.androidcleanarchitecture

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.util.Size
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.farhan.tanvir.androidcleanarchitecture.presentation.navigation.NavGraph
import com.farhan.tanvir.androidcleanarchitecture.ui.theme.AndroidCleanArchitectureTheme
import com.farhan.tanvir.androidcleanarchitecture.util.QRCodeFoundListener
import com.farhan.tanvir.androidcleanarchitecture.util.QRCodeImageAnalyzer
import com.farhan.tanvir.androidcleanarchitecture.util.SocketHandler
import com.google.common.util.concurrent.ListenableFuture
import dagger.hilt.android.AndroidEntryPoint

private const val PERMISSIONS_REQUEST_CODE = 0
private val PERMISSIONS_REQUIRED = arrayOf(Manifest.permission.CAMERA)

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    companion object {
        fun hasPermissions(context: Context) = PERMISSIONS_REQUIRED.all {
            ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }
    }

    private var previewView: PreviewView? = null
    private var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>? = null
    private var qrCodeFoundButton: Button? = null
    private var qrCode : String? = null

    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidCleanArchitectureTheme {
                navController = rememberNavController()
                NavGraph(navController = navController)
            }
        }

       // cameraProviderFuture = ProcessCameraProvider.getInstance(this);

       // initSocket()
    }

    fun initSocket(){
        SocketHandler.setSocket()
        SocketHandler.establishConnection()

        val mSocket = SocketHandler.getSocket()

        //Define Responses
        mSocket.on("eventName") { args ->
            if (args[0] != null) {
                val counter = args[0] as Int
                Log.i("I",counter.toString())
                runOnUiThread {
                    // The is where you execute the actions after you receive the data
                }
            }
        }
    }

    fun emitSocket(){
        val mSocket = SocketHandler.getSocket()
        //Actions
        mSocket.emit("eventName", "test variable")
    }

    //Permissions
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            if (PackageManager.PERMISSION_GRANTED == grantResults.firstOrNull() && hasPermissions(this)) {
                //success, show camera
                Log.d("TIME123", "Permissions granted.")

            } else {
                //request denied, keep loading screen visible
            }
        }
    }

    private fun startCamera() {
        cameraProviderFuture!!.addListener({
            try {
                val cameraProvider = cameraProviderFuture!!.get()
                bindCameraPreview(cameraProvider)
            } catch (e: Exception) {
                Toast.makeText(this, "Error starting camera " + e.message, Toast.LENGTH_SHORT).show()
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun bindCameraPreview(cameraProvider: ProcessCameraProvider) {
        //previewView!!.preferredImplementationMode = PreviewView.ImplementationMode.SURFACE_VIEW
        val preview = Preview.Builder()
            .build()
        val cameraSelector = CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
            .build()
        //preview.setSurfaceProvider(previewView!!.createSurfaceProvider())
        val imageAnalysis = ImageAnalysis.Builder()
            .setTargetResolution(Size(1280, 720))
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()
        imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(this), QRCodeImageAnalyzer(object :
            QRCodeFoundListener {
            override fun onQRCodeFound(_qrCode: String) {
                qrCode = _qrCode
                qrCodeFoundButton!!.visibility = View.VISIBLE
            }

            override fun qrCodeNotFound() {
                qrCodeFoundButton!!.visibility = View.INVISIBLE
            }
        }))
        val camera = cameraProvider.bindToLifecycle((this as LifecycleOwner), cameraSelector, imageAnalysis, preview)
    }
}

