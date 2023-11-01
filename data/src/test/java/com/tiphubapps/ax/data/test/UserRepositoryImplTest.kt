package com.tiphubapps.ax.data.test

import android.util.Log
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.tiphubapps.ax.data.repository.UserRepositoryImpl
import com.tiphubapps.ax.data.entity.UserEntity
import com.tiphubapps.ax.data.repository.dataSource.Result
import com.tiphubapps.ax.domain.model.User
import com.tiphubapps.ax.domain.repository.UseCaseResult
import com.tiphubapps.ax.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
internal class UserRepositoryImplTest {

    private val task1 = UserEntity(0, 1, "a1", 0, 0, 0, "[]", "[]", "[]", "[]", "[]", 0, "test@email.com", "test 1", "", "", "", "","","","")

    private val task2 = UserEntity(1, 2, "b2", 0, 0, 0,
        "[]", "[]", "[]", "[]", "[]", 0,
        "test2@email.com", "test 2", "", "", "",
        "","","","")

    private val task3 = UserEntity(2, 3, "c3", 0, 0, 0,
        "[]", "[]", "[]", "[]", "[]", 0,
        "test3@email.com", "test 3", "", "", "",
        "","","","")


    private val remoteUsers = listOf(task1, task2).sortedBy { it.id }
    private val localUsers = listOf(task3).sortedBy { it.id }
    private val newUsers = listOf(task3).sortedBy { it.id }


    private val user1 = User(0, 1, "a1", 0, 0, 0, "[]", "[]", "[]", "[]", "[]", 0, "test@email.com", "test 1", "", "", "", "","","","")

    private val user2 = User(1, 2, "b2", 0, 0, 0,
        "[]", "[]", "[]", "[]", "[]", 0,
        "test2@email.com", "test 2", "", "", "",
        "","","","")

    private val user3 = User(2, 3, "c3", 0, 0, 0,
        "[]", "[]", "[]", "[]", "[]", 0,
        "test3@email.com", "test 3", "", "", "",
        "","","","")


    private val realUsers = listOf(user1, user2).sortedBy { it.id }


    private lateinit var usersRemoteDataSource: FakeRemoteDataSource
    private lateinit var usersLocalDataSource: FakeLocalDataSource

    // Class under test
    private lateinit var usersRepository: UserRepositoryImpl

    @Before
    fun createRepository(){
        //Initialize Data
        usersRemoteDataSource = FakeRemoteDataSource(remoteUsers.toMutableList())
        usersLocalDataSource = FakeLocalDataSource(localUsers.toMutableList())

        usersRepository = UserRepositoryImpl(usersRemoteDataSource, usersLocalDataSource, Dispatchers.Unconfined)

    }

    @Test
    fun getToken() {

        //Hypothesis
        val expected = null

        //Experiment
        val actual = usersRepository.token

        //Evaluate
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun setToken() {
        //Hypothesis
        val expected = "test"
        //Experiment
        usersRepository.setCurrentToken("test")
        //Evaluate
        val actual = usersRepository.token
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun getCurrentUser() {
        //Hypothesis
        val expected = null
        //Experiment
        val actual = usersRepository.currentUser
        //Evaluate
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun setCurrentUser() {
        //Hypothesis
        val expected = "test"
        //Experiment
        usersRepository.currentUser = "test"
        //Evaluate
        val actual = usersRepository.currentUser
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun getLocalValue() {
        //Hypothesis
        val expected = "Initial Value"
        //Experiment
        val actual = usersRepository.getLocalValueFlow().value
        //Evaluate
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun isLoggedIn() {
        //Hypothesis
        val expected = false
        //Experiment
        val actual = usersRepository.isLoggedIn
        //Evaluate
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun updateLocalValue() {
        //Hypothesis
        val expected = "test"
        //Experiment
        usersRepository.updateLocalValue("test")
        //Evaluate
        val actual = usersRepository.localValue.value
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun testGetCurrentUser() = runBlockingTest {
        //Hypothesis
        val expected = UseCaseResult.UseCaseSuccess(user1)
        //Experiment
        val actual = usersRepository.getCurrentUser()
        //Evaluate
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun getCurrentUserWithToken() = runBlockingTest {
        //Hypothesis
        val expected = UseCaseResult.UseCaseSuccess(user1)
        //Experiment
        val actual = usersRepository.getCurrentUserWithToken("")
        //Evaluate
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun getUserById() = runBlockingTest {
        //Hypothesis
        val expected = UseCaseResult.UseCaseSuccess(user1)
        //Experiment
        val actual = usersRepository.getUserById("1","")
        //Evaluate
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun getUsersById() = runBlockingTest {
        //Hypothesis
        val expected = UseCaseResult.UseCaseSuccess(realUsers)
        //Experiment
        val actual = usersRepository.getUsersById(JsonArray(0), JsonArray(0),"")
        //Evaluate
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun createSessionByUsers() = runBlockingTest {
        //Hypothesis
        val expected = JsonObject().also{
            it.addProperty("_id","1")
            it.addProperty("previous", -1)
        }
        //Experiment
        val actual = usersRepository.createSessionByUsers(JsonObject(), "")
        //Evaluate
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun getAllUsers() = runBlockingTest{
        //Hypothesis
        val expected = UseCaseResult.UseCaseSuccess(realUsers)
        //Experiment
        /*val actual = when (val exp = usersRepository.getAllUsers()){
            is Result.Success<*> -> exp.data
            else -> {}
        }*/
        val actual = usersRepository.getAllUsers()
        //Evaluate
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun getAllUsersWithToken() {
        runBlockingTest{
            //Hypothesis
            val expected = UseCaseResult.UseCaseSuccess(realUsers)
            //Experiment
            /*val actual = when (val exp = usersRepository.getAllUsers()){
                is Result.Success<*> -> exp.data
                else -> {}
            }*/
            val actual = usersRepository.getAllUsersWithToken("")
            //Evaluate
            Assert.assertEquals(expected, actual)
        }
    }

    @Test
    fun getUsersFromDB() = runBlockingTest {
            //Hypothesis
            val expected = user3
            lateinit var actual : User
            //Experiment
            val new = usersRepository.getUsersFromDB(3)

            new.collect{
                println("TIME123 COllection value: ${it}")
                if (it != null) {
                    actual = it
                }
            }

            new.collectLatest{
                println("TIME123 COllection value: ${it}")
                if (it != null) {
                    actual = it
                }
            }

        println("TIME123 COllection END")
            //Evaluate
            Assert.assertEquals(expected, actual)
    }

    @Test
    fun postLogin() = runBlockingTest {
        //Hypothesis
        val expected = "test token"
        //Experiment
        usersRepository.postLogin("", "")
        //Evaluate
        val actual = usersRepository.token
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun getCurrentToken() {
        //Hypothesis
        val expected = null
        //Experiment
        val actual = usersRepository.getCurrentToken()
        //Evaluate
        Assert.assertEquals(expected, actual)
    }
    @Test
    fun setCurrentToken() {
        //Hypothesis
        val expected = "test"
        //Experiment
        usersRepository.setCurrentToken("test")
        //Evaluate
        val actual = usersRepository.getCurrentToken()
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun getLocalValueFlow() {
        //Hypothesis
        val expected = "Initial Value"
        //Experiment
        val actual = usersRepository.localValue.value
        //Evaluate
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun logout() = runBlockingTest {
        //Hypothesis
        val expected = true
        //Experiment
        val actual = usersRepository.logout()
        //Evaluate
        Assert.assertEquals(expected, actual)
    }
}