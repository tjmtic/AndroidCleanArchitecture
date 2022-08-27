package com.farhan.tanvir.androidcleanarchitecture

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.farhan.tanvir.androidcleanarchitecture.presentation.navigation.NavGraph
import com.farhan.tanvir.androidcleanarchitecture.ui.theme.AndroidCleanArchitectureTheme
import com.farhan.tanvir.androidcleanarchitecture.util.SocketHandler
import dagger.hilt.android.AndroidEntryPoint

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
}

