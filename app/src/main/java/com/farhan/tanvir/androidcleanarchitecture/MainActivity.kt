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



    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidCleanArchitectureTheme {
                navController = rememberNavController()
                NavGraph(navController = navController)
            }
        }


        initSocket()


    }

    fun initSocket(){
        println("Initializing socket...")
        SocketHandler.setSocket()
        SocketHandler.establishConnection()

        val mSocket = SocketHandler.getSocket()

        //emitSocketCheckIn()

        //Define Responses
        mSocket.on("eventName") { args ->
            println("eventname socket...")

            if (args[0] != null) {
                val counter = args[0] as Int
                Log.i("I",counter.toString())
                runOnUiThread {
                    // The is where you execute the actions after you receive the data
                }
            }
        }

        mSocket.on("receiver-tip") { args ->
            println("receiver-tip socket...")

            if (args[0] != null) {
                val counter = args[0] as Int
                Log.i("I",counter.toString())
                runOnUiThread {
                    // The is where you execute the actions after you receive the data
                }
            }
        }

        mSocket.on("receiver-tip-rain") { args ->
            println("receiver tip rain socket...")

            if (args[0] != null) {
                val counter = args[0] as Int
                Log.i("I",counter.toString())
                runOnUiThread {
                    // The is where you execute the actions after you receive the data
                }
            }
        }

        mSocket.on("refresh") { args ->
            println("refersh  socket...")

            if (args[0] != null) {
                val counter = args[0] as Int
                Log.i("I",counter.toString())
                runOnUiThread {
                    // The is where you execute the actions after you receive the data
                }
            }
        }

        mSocket.on("ack") { args ->
            println("ack socket...")

            if (args[0] != null) {
                val counter = args[0] as Int
                Log.i("I",counter.toString())
                runOnUiThread {
                    // The is where you execute the actions after you receive the data
                }
            }
        }

        mSocket.on("websocketUpgrade") { args ->
            println("webscoket upgrade socket...")

            if (args[0] != null) {
                val counter = args[0] as Int
                Log.i("I",counter.toString())
                runOnUiThread {
                    // The is where you execute the actions after you receive the data
                }
            }
        }

       /* mSocket.on("checkIn") { args ->
            if (args[0] != null) {
                val counter = args[0] as Int
                Log.i("I",counter.toString())
                runOnUiThread {
                    // The is where you execute the actions after you receive the data

                    //get user id
                }
            }
        }*/

        println("Socket...Initialized.")

       // emitSocket()
        mSocket.emit("checkIn", "62733392e00b1f1813b0b610")
        //mSocket.emit("checkIn", "5e22b8a4bf397f08932de490")

    }

    fun emitSocket(){
        val mSocket = SocketHandler.getSocket()
        //Actions
        mSocket.emit("eventName", "test variable")

        println("emit eventNsame socket...")

    }

    fun emitSocketCheckIn(){
        val mSocket = SocketHandler.getSocket()
        //Actions
        mSocket.emit("checkIn", "5e22b8a4bf397f08932de490")

        println("emit checkit socket...")

    }

}

