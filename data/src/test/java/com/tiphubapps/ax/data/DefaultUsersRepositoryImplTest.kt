package com.tiphubapps.ax.data

import com.tiphubapps.ax.data.entity.UserEntity
import com.tiphubapps.ax.data.repository.DefaultUsersRepositoryImpl
import com.tiphubapps.ax.domain.model.User
import com.tiphubapps.ax.domain.repository.UseCaseResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

internal class DefaultUsersRepositoryImplTest {

    private val task1 = UserEntity(0, 1, "a1", 0, 0, 0, "[]", "[]", "[]", "[]", "[]", 0, "test@email.com", "test 1", "", "", "", "","","","")

    private val task2 = UserEntity(1, 2, "b2", 0, 0, 0,
        "[]", "[]", "[]", "[]", "[]", 0,
        "test2@email.com", "test 2", "", "", "",
        "","","","")

    private val task3 = UserEntity(2, 3, "c3", 0, 0, 0,
        "[]", "[]", "[]", "[]", "[]", 0,
        "test3@email.com", "test 3", "", "", "",
        "","","","")


    private val remoteUserEntities = listOf(task1, task2).sortedBy { it.id }
    private val localUserEntities = listOf(task3).sortedBy { it.id }
    private val newUserEntities = listOf(task3).sortedBy { it.id }


    private val user1 = User(0, 1, "a1", 0, 0, 0, "[]", "[]", "[]", "[]", "[]", 0, "test@email.com", "test 1", "", "", "", "","","","")

    private val user2 = User(1, 2, "b2", 0, 0, 0,
        "[]", "[]", "[]", "[]", "[]", 0,
        "test2@email.com", "test 2", "", "", "",
        "","","","")

    private val user3 = User(2, 3, "c3", 0, 0, 0,
        "[]", "[]", "[]", "[]", "[]", 0,
        "test3@email.com", "test 3", "", "", "",
        "","","","")


    private val remoteUsers = listOf(user1, user2).sortedBy { it.id }
    private val localUsers = listOf(user3).sortedBy { it.id }
    private val newUsers = listOf(user3).sortedBy { it.id }


    private lateinit var usersRemoteDataSource: FakeDefaultDataSource
    private lateinit var usersLocalDataSource: FakeDefaultDataSource

    // Class under test
    private lateinit var usersRepository: DefaultUsersRepositoryImpl

    @Before
    fun setUp() {

        usersRemoteDataSource = FakeDefaultDataSource(remoteUserEntities.toMutableList())
        usersLocalDataSource = FakeDefaultDataSource(localUserEntities.toMutableList())

        usersRepository = DefaultUsersRepositoryImpl(usersRemoteDataSource, usersLocalDataSource, Dispatchers.Unconfined)
    }

    @Test
    fun getUsers() = runBlockingTest {
        //Hypothesis
        val expected = UseCaseResult.UseCaseSuccess(localUsers)
        //Experiment
        val actual = usersRepository.getUsers(false)
        //Evaluate
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun getUsersWithRefresh() = runBlockingTest {
        //Hypothesis
        val expected = UseCaseResult.UseCaseSuccess(remoteUsers)
        //Experiment
        val actual = usersRepository.getUsers(true)
        //Evaluate
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun refreshUsers() = runBlockingTest {

        //Hypothesis
        val expected = Pair(UseCaseResult.UseCaseSuccess(localUsers),
                            UseCaseResult.UseCaseSuccess(remoteUsers))
        //Experiment
        val actual1 = usersRepository.getUsers(false)
        val actual2 = usersRepository.getUsers(true)
        val actual = Pair(actual1, actual2)
        //Result
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun observeUsers() = runBlockingTest{
        //Hypothesis
        val expected = UseCaseResult.UseCaseSuccess((localUsers))
        //Experiment
        var actual : UseCaseResult<List<User>>? = null
        user3.userId?.let { usersRepository.observeUsers() }.run{ this!!.collect{actual = it}}
        //Evaluate
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun refreshUser() {
        //TODO: Implement Data Sync / Refresh for LOCAL/REMOTE DATA
    }

    @Test
    fun observeUser() = runBlockingTest {
        //Hypothesis
        val expected = UseCaseResult.UseCaseSuccess((user3))
        //Experiment
        var actual : UseCaseResult<User>? = null
        user3.userId?.let { usersRepository.observeUser(it) }.run{ this!!.collect{actual = it}}
        //Evaluate
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun getUser() = runBlockingTest {
        //Hypothesis
        val expected = UseCaseResult.UseCaseSuccess(user1)
        //Experiment
        val actual = user1.userId?.let { usersRepository.getUser(it, true) }
        //Evaluate
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun saveUser() = runBlockingTest {
        //Hypothesis
        val expected = Pair(UseCaseResult.UseCaseSuccess(localUsers),
            UseCaseResult.UseCaseSuccess(localUsers.plus(user2)))
        //Experiment
        val actual1 = usersRepository.getUsers(false)
        usersRepository.saveUser(user2)
        val actual2 = usersRepository.getUsers(false)
        val actual = Pair(actual1, actual2)
        //Result
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun completeUser() {
        //Not Implemented
    }

    @Test
    fun testCompleteUser() {
        //Not Implemented?
    }

    @Test
    fun activateUser() {
        //Not Implemented
    }

    @Test
    fun testActivateUser() {
        //Not Implemented
    }

    @Test
    fun clearCompletedUsers() {
        //Not Implemented
    }

    @Test
    fun deleteAllUsers() = runBlockingTest {
        //Hypothesis
        val expected = Pair(UseCaseResult.UseCaseSuccess(localUsers),
            UseCaseResult.UseCaseSuccess(emptyList<User>()))
        //Experiment
        val actual1 = usersRepository.getUsers(false)
        usersRepository.deleteAllUsers()
        val actual2 = usersRepository.getUsers(false)
        val actual = Pair(actual1, actual2)
        //Result
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun deleteUser() = runBlockingTest {
        //Hypothesis
        val expected = Pair(UseCaseResult.UseCaseSuccess(localUsers),
            UseCaseResult.UseCaseSuccess(localUsers.minus(user3)))
        //Experiment
        val actual1 = usersRepository.getUsers(false)
        user3.userId?.let { usersRepository.deleteUser(it) }
        val actual2 = usersRepository.getUsers(false)
        val actual = Pair(actual1, actual2)
        //Result
        Assert.assertEquals(expected, actual)
    }
}