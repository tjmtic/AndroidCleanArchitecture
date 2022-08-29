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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.farhan.tanvir.androidcleanarchitecture.presentation.navigation.NavGraph
import com.farhan.tanvir.androidcleanarchitecture.ui.theme.AndroidCleanArchitectureTheme
import com.farhan.tanvir.androidcleanarchitecture.util.QRCodeFoundListener
import com.farhan.tanvir.androidcleanarchitecture.util.QRCodeImageAnalyzer
import com.farhan.tanvir.androidcleanarchitecture.util.SocketHandler
import com.google.common.util.concurrent.ListenableFuture
import dagger.hilt.android.AndroidEntryPoint
import io.socket.client.IO
import io.socket.client.Socket
import kotlinx.coroutines.*

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
        //SocketHandler.setSocket()
        val authToken = "eyJraWQiOiJ1WnFRMFlBQk45N0VUY0JWZ3NBR2N0NFdab0cxVzhuQThtUWhXSFV3R0NrPSIsImFsZyI6IlJTMjU2In0.eyJzdWIiOiJjMmUyY2U5Yi0yYTRmLTQ3ZmYtYWFkYS1kZWIyY2M2MDkxNDgiLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsImlzcyI6Imh0dHBzOlwvXC9jb2duaXRvLWlkcC51cy1lYXN0LTEuYW1hem9uYXdzLmNvbVwvdXMtZWFzdC0xX2xXb0lZRWJwZyIsImNvZ25pdG86dXNlcm5hbWUiOiJjMmUyY2U5Yi0yYTRmLTQ3ZmYtYWFkYS1kZWIyY2M2MDkxNDgiLCJhdWQiOiIzaTBtZDZrM2s0OTkyNWY2ZWsxYmRyNTFlYiIsImV2ZW50X2lkIjoiNDFlZWY3NWMtOTQ0Zi00ZDgxLWE4MjUtYjgwNTlhZDhiZDlhIiwidG9rZW5fdXNlIjoiaWQiLCJhdXRoX3RpbWUiOjE2NjE2NTIzODMsIm5hbWUiOiJKYXkiLCJleHAiOjE2NjE2NTU5ODMsImlhdCI6MTY2MTY1MjM4MywiZW1haWwiOiJqYXlAMDI2MHRlY2guY29tIn0.IbPgoHI43KiQ1gi-quPtP-4Y4AC-XesQ2mqaGKdtlmMq5pSnuO3sRNeCUjNMZzMRPsJnasx5Bh9lQwF6NGMjUO4c10vu6SnM4SLz2OGUEWccYU01QclgIyYaEbLNii6j8NtIfLt0Tc2GNUkFWMdfj9Y7JC0oFjswlABZO8FtfHDyUMhENOEpJzqTMFqWIxOVHBo4_A5lxoIX9b18jDBOnFC7osQrCXcx4etemmnHetxQvftRyBB0FZBmXwrmCJaqOjW2CrHrlXjkDW-oZS9LPVcIFLVvSPywYDpNKkKV1qAfQWYf3r4k5iHtm0TWD_VBeO1IMXbG0TADx-NCULpCQQ"
        //val authToken = "eyJraWQiOiJ1WnFRMFlBQk45N0VUY0JWZ3NBR2N0NFdab0cxVzhuQThtUWhXSFV3R0NrPSIsImFsZyI6IlJTMjU2In0.eyJzdWIiOiIyNWFhZTc5NS0wYjNhLTRlNmEtYWU2ZC0wZDk3NmMxMGM0NDAiLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsImlzcyI6Imh0dHBzOlwvXC9jb2duaXRvLWlkcC51cy1lYXN0LTEuYW1hem9uYXdzLmNvbVwvdXMtZWFzdC0xX2xXb0lZRWJwZyIsImNvZ25pdG86dXNlcm5hbWUiOiIyNWFhZTc5NS0wYjNhLTRlNmEtYWU2ZC0wZDk3NmMxMGM0NDAiLCJhdWQiOiIzaTBtZDZrM2s0OTkyNWY2ZWsxYmRyNTFlYiIsImV2ZW50X2lkIjoiYmUyNzI1MmYtMWZiNC00ODlmLTk0ZmMtN2MxNDYxNDU5NjZiIiwidG9rZW5fdXNlIjoiaWQiLCJhdXRoX3RpbWUiOjE2NjE2NTMzMDEsIm5hbWUiOiJBVGVzdGVyc29uIiwiZXhwIjoxNjYxNjU2OTAxLCJpYXQiOjE2NjE2NTMzMDEsImVtYWlsIjoidGVzdEB0ZXN0aWVzLmNvbSJ9.hTDuLDbG9WZnvwbiYSqa2yktrVqTgulK7u9sutOmiIMWheHHida7IrFA0QkD7XZpZ8h24VoCNRJG7GbTHAosUIUonHWzV6TsDEEg3wjwqFWCWEcIlCYzNkT98hJmOJGCobMYxU_eCmKbLHDcGznWPRy0Ch6AzvOkMgX_vFoB9oDGlKU6p_Eo_vXJVGaLp9fF1VXJWevZlOdsHarCjtmcvt41rzQLnE9KAx1OlDAuc1Pgh0_2Gk1-aa_69-NvYvGWpSvbV_7bKs6sP0PvZet5Dp9ItL3KkZw8ovBRTBo-ATKsDqqS-qAfmfeNxTs0lJ3Z57L-TLiQyQoOtIbNkEEJFw"
        val tokenList: List<String> = emptyList<String>().plus(authToken)
        var headers = mapOf("token" to tokenList)//List<String, List<String>>(1)



        // headers.set("token", tokenList)
        val opts = IO.Options()
        opts.reconnection = true
        opts.reconnectionAttempts = -1
        opts.reconnectionDelay = 3
        opts.reconnectionDelayMax = 3
        opts.forceNew = true
        opts.extraHeaders = headers
        opts.query = "token=" + authToken;
        var mSocket: Socket = IO.socket(BuildConfig.SOCKET_URL, opts)

        //SocketHandler.establishConnection()

        val s = mSocket.connect()


        if(s.connected()){
            println("CONNCEECEECETED")
        }
        else {
            println("NOT OCNNENCERTEDEE")
        }

        //val mSocket = SocketHandler.getSocket()

        //emitSocketCheckIn()

        mSocket.emit("checkIn", "5e22b8a4bf397f08932de490")

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

        mSocket.on("connection") { args ->
            println("connection socket...")

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

        mSocket.on("message") { args ->
            println("message from socket...")

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

        println("Socket...Initialized1.")

       // emitSocket()
       // mSocket.emit("checkIn", "62733392e00b1f1813b0b610")
        //mSocket.emit("checkIn", "5e22b8a4bf397f08932de490")
        println("Socket...Initialized2.")

        mSocket.emit("whoami", "");
        println("Socket...Initialized3.")

        mSocket.emit("test");


        if(s.connected()){
            println("CONNCEECEECETED")
        }
        else {
            println("NOT OCNNENCERTEDEE")
        }

        doTimeout(s)

    }

    fun doTimeout(socket: Socket, count:Int = 0){
        lifecycleScope.launch(Dispatchers.IO, CoroutineStart.DEFAULT) {
           // withTimeout(5000, {
            delay(5000)

                if (socket.connected()) {
                    println("CONNCEECEECETED${count}")
                } else {
                    println("NOT OCNNENCERTEDEE${count}")
                    doTimeout(socket, count+1)
                }
           // })

          //  delay(5000)
        }
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

