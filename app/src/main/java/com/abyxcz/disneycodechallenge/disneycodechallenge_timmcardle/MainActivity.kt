package com.abyxcz.disneycodechallenge.disneycodechallenge_timmcardle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.abyxcz.disneycodechallenge.disneycodechallenge_timmcardle.presentation.navigation.NavGraph
import com.abyxcz.disneycodechallenge.disneycodechallenge_timmcardle.ui.theme.DisneyCodeChallenge_TimMcArdleTheme
import com.abyxcz.disneycodechallenge.domain.model.User
import com.abyxcz.disneycodechallenge.domain.useCase.UserUseCases
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
            DisneyCodeChallenge_TimMcArdleTheme {
                navController = rememberNavController()
                NavGraph(navController = navController)
            }
        }

        initUsers(BuildConfig.NUM_USERS)
    }

    fun initUsersFromFile(){
        //CSV file can be uploaded to the application root folder, sample file provided in src
        val file = File(applicationContext.filesDir, "testUsers.csv")
        val usersList = mutableListOf<User>()
        file.forEachLine {
            var line = it.split(",")
            usersList.add(User(0, line[0].trim().toInt(), line[1], line[2].trim().toBoolean(), false ))
        }

        updateUsers(usersList)

    }

    fun initUsers(numUsers: Int){
        val usersList = mutableListOf<User>()

        for (i in 1..numUsers){
            usersList.add(User(0, i, "User${i}", i % 2 == 0, false))
            println("Adding user ${usersList.elementAt(usersList.size-1)}")
        }

        updateUsers(usersList)

    }

    fun updateUsers(usersList: List<User>){
        mainScope.launch(
            Dispatchers.IO, CoroutineStart.DEFAULT
        ) {
            userUseCases.deleteAllUsersUseCase.invoke();
            userUseCases.insertNewUsersUseCase.invoke(usersList);
        }
    }

    override fun onDestroy(){
        super.onDestroy()
        mainScope.cancel()
    }
}

