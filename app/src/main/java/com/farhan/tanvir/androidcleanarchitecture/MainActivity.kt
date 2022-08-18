package com.farhan.tanvir.androidcleanarchitecture

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.farhan.tanvir.androidcleanarchitecture.presentation.navigation.NavGraph
import com.farhan.tanvir.androidcleanarchitecture.ui.theme.AndroidCleanArchitectureTheme
import com.farhan.tanvir.domain.model.User
import com.farhan.tanvir.domain.useCase.UserUseCases
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject lateinit var userUseCases: UserUseCases
    private lateinit var navController: NavHostController
    private val mainScope = MainScope()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidCleanArchitectureTheme {
                navController = rememberNavController()
                NavGraph(navController = navController)
            }
        }

       // initUsers()
       /* initUsers()
        initUsers()
        initUsers()
        initUsers()
        initUsers()
        initUsers()
        initUsers()
        initUsers()
        initUsers()
        initUsers()*/
    }

    fun initUsers(){

        val file = File(applicationContext.filesDir, "testUsers.csv")
        val usersList = mutableListOf<User>()
        file.forEachLine {
            var line = it.split(",")
            usersList.add(User(0, line[0]!!.toInt(), line[1]!!, line[2]!!.trim().toBoolean(), false ))
            usersList.add(User(0, line[0]!!.toInt(), line[1]!!, line[2]!!.trim().toBoolean(), false ))
            usersList.add(User(0, line[0]!!.toInt(), line[1]!!, line[2]!!.trim().toBoolean(), false ))
            usersList.add(User(0, line[0]!!.toInt(), line[1]!!, line[2]!!.trim().toBoolean(), false ))
            usersList.add(User(0, line[0]!!.toInt(), line[1]!!, line[2]!!.trim().toBoolean(), false ))
            usersList.add(User(0, line[0]!!.toInt(), line[1]!!, line[2]!!.trim().toBoolean(), false ))
            usersList.add(User(0, line[0]!!.toInt(), line[1]!!, line[2]!!.trim().toBoolean(), false ))
            usersList.add(User(0, line[0]!!.toInt(), line[1]!!, line[2]!!.trim().toBoolean(), false ))
            usersList.add(User(0, line[0]!!.toInt(), line[1]!!, line[2]!!.trim().toBoolean(), false ))
            usersList.add(User(0, line[0]!!.toInt(), line[1]!!, line[2]!!.trim().toBoolean(), false ))
            usersList.add(User(0, line[0]!!.toInt(), line[1]!!, line[2]!!.trim().toBoolean(), false ))
            usersList.add(User(0, line[0]!!.toInt(), line[1]!!, line[2]!!.trim().toBoolean(), false ))

            println(line)
            println(line[2]!!.trim().toBoolean())
        }

        mainScope.launch(
            Dispatchers.IO, CoroutineStart.DEFAULT
        ) {
            //userUseCases.deleteAllUsersUseCase.invoke();
            userUseCases.insertNewUsersUseCase.invoke(usersList);
        }

    }

    override fun onDestroy(){
        super.onDestroy()
        mainScope.cancel()
    }
}

