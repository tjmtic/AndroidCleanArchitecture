package com.tiphubapps.ax.rain

import android.Manifest
import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnticipateInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.tiphubapps.ax.rain.presentation.navigation.NavGraph
import com.tiphubapps.ax.rain.ui.theme.AndroidCleanArchitectureTheme
//import com.github.nkzawa.engineio.client.Socket
//import com.github.nkzawa.socketio.client.IO
import com.google.gson.JsonParser
import dagger.hilt.android.AndroidEntryPoint
//import io.socket.client.IO
//import io.socket.client.Socket
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import java.util.*

private const val PERMISSIONS_REQUEST_CODE = 0
private val PERMISSIONS_REQUIRED = arrayOf(Manifest.permission.CAMERA)

@AndroidEntryPoint
class MainActivity : ComponentActivity() {



    private lateinit var navController: NavHostController
    var contentHasLoaded = false;

    private var ws: WebSocket? = null
    private val client by lazy { OkHttpClient() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val splashScreen = installSplashScreen()

       // var remainingDuration: Long = 0L;

        splashScreen.setOnExitAnimationListener { splashScreenView ->

            // Create your custom animation.
            val slideUp = ObjectAnimator.ofFloat(
                splashScreenView.view,
                View.TRANSLATION_Y,
                0f,
                -splashScreenView.view.height.toFloat()
            )
            slideUp.interpolator = AnticipateInterpolator()
            slideUp.duration = 200L

            // Call SplashScreenView.remove at the end of your custom animation.
            slideUp.doOnEnd { splashScreenView.remove() }

            // Run your animation.
            slideUp.start()
        }

/*
        splashScreen.setOnExitAnimationListener { splashScreenView ->
            val slideBack = ObjectAnimator.ofFloat(
                splashScreenView.view,
                View.TRANSLATION_X,
                0f,
                -splashScreenView.view.width.toFloat()
            ).apply {
                interpolator = DecelerateInterpolator()
                duration = 800L
                doOnEnd { splashScreenView.remove() }
            }

            slideBack.start()
        }
*/

        val loggedInUser = (applicationContext as Rain).getEncryptedPreferencesValue("userToken");



        setContent {
            AndroidCleanArchitectureTheme {
                navController = rememberNavController()
                NavGraph(navController = navController)
            }
        }

        // Set up an OnPreDrawListener to the root view.
        /*val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    // Check if the initial data is ready.

                    return if (contentHasLoaded) {
                        // The content is ready; start drawing.
                        content.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    } else {

                        false
                    }
                }
            }
        )*/


        //initSocket()
       // start()


    }

    private fun setupEncryptedPreferences(){
        val context = applicationContext
        val mainKey = MasterKey.Builder(applicationContext)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        val sharedPreferences = EncryptedSharedPreferences.create(
            applicationContext,
            "TipHubSharPrefEncr",
            mainKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        with (sharedPreferences.edit()) {

            // write all the data entered by the user in SharedPreference and apply
            putString("test", "test text")
            putString("userToken", "0x0")
            apply()
        }
    }

    private fun setEncryptedPreferences(key: String, value: String){
        val context = applicationContext
        val mainKey = MasterKey.Builder(applicationContext)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        val sharedPreferences = EncryptedSharedPreferences.create(
            applicationContext,
            "TipHubSharPrefEncr",
            mainKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        with (sharedPreferences.edit()) {

            // write all the data entered by the user in SharedPreference and apply
            putString(key, value)
            apply()
        }
    }

    private fun getEncryptedPreferencesValue(key: String): String?{
        val context = applicationContext
        val mainKey = MasterKey.Builder(applicationContext)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        val sharedPreferences = EncryptedSharedPreferences.create(
            applicationContext,
            "TipHubSharPrefEncr",
            mainKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        return sharedPreferences.getString(key, "");
    }
    private fun start() {
        val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6IjYzMWJhZWM3OTA0ZDQ3ZmExMzQ4YzgyZCIsInVzZXJuYW1lIjoiMTIxMzU1NTEyMTIiLCJleHBpcmUiOjE2ODI3NDQ5MDQ5Mzh9.WAnFXtzPFeWsff6iXv_zUF5CBZhdadbSzNcjgtRCLk0";

        val request: Request =
         //   Request.Builder().url("ws://34.122.212.113/").build()
        Request.Builder().url("ws://3.239.168.240/").addHeader("Authorization", "Bearer $token").build()
        val listener = object: WebSocketListener() {
            override fun onMessage(webSocket: WebSocket, text: String) {
                val currentTime: Date = Calendar.getInstance().time
                //exampleUiState.addMessage(
                 //   Message("Web", text, currentTime.toString()))
                Log.d("TIME123", "From SOCKET:" + text)
                handleReceivedText(text)

            }
        }
        ws = client.newWebSocket(request, listener)

        Log.d("TIME123", "SOCKET CONNECTED?")
        ws?.send("ANDROID MESSAGE SENT");

        /*lifecycleScope.launch(Dispatchers.IO, CoroutineStart.DEFAULT) {
            delay(5000)
            contentHasLoaded = true;

            ws?.send("ANDROID MESSAGE SENT");
        }*/
       // timer();

    }

    fun timer(){
        //lifecycleScope.launch(Dispatchers.Main, CoroutineStart.DEFAULT) {
         //   delay(5000)
            contentHasLoaded = true;
            println("TIME123 Sending message...")

            ws?.send("ANDROID MESSAGE SENT");

            ws?.let {
                println("TIME123 Sending message... SENT")
            }

        //    timer()
       // }
    }

    /*fun initSocket(){
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
        //opts.extraHeaders = headers
        opts.path = "/socket.io"
        opts.query = "token=" + authToken;
        var mSocket: com.github.nkzawa.socketio.client.Socket = IO.socket(BuildConfig.SOCKET_URL, opts)

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

    }*/

    /*fun doTimeout(socket: com.github.nkzawa.socketio.client.Socket, count:Int = 0){
        lifecycleScope.launch(Dispatchers.IO, CoroutineStart.DEFAULT) {
           // withTimeout(5000, {
            delay(5000)

                if (socket.connected()) {
                    println("CONNCEECEECETED${count}")
                } else {
                    println("NOT OCNNENCERTEDEE${count}")
                   // doTimeout(socket, count+1)
                }

                contentHasLoaded = true;

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

    }*/


    private fun handleReceivedText(text: String) {
        // 1. Parse the incoming text as a JSON object
        // Replace this part with your JSON parsing library if you use a different one
        val jsonObject = JsonParser.parseString(text).asJsonObject

        // 2. Extract the 'action' property from the JSON object
        val action = jsonObject["action"]?.asString

        // 3. Use the extracted 'action' in the when statement
        when (action) {
            // Handle actions here
            // Make sure to update the code to match your current Swift implementation
            "RECEIVER_TIP" -> {
                // Handle the RECEIVER_TIP action
                println("Socket Action: RECEIVER_TIP")

            }
            "RECEIVER_TIP_RAIN" -> {
                // Handle the RECEIVER_TIP_RAIN action
                println("Socket Action: RECEIVER_TIP_RAIN")
            }
            "THANK_YOU" -> {
                // Handle the THANK_YOU action
                println("Socket Action: THANK_YOU")
            }
            "ACK" -> {
                // Handle the ACK action
                println("Socket Action: ACK")
            }
            "REFRESH" -> {
                // Handle the REFRESH action
                println("Socket Action: REFRESH")
            }
            "RECEIVER_TIP_BAG" -> {
                // Handle the RECEIVER_TIP_BAG action
                println("Socket Action: RECEIVER_TIP_BAG")
            }
            else -> {
                println("Unknown action")
            }
        }
    }

    override fun onResume() {
        super.onResume()
       // start()
    }

}

