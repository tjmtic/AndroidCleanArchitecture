package com.tiphubapps.ax.data.test

import com.tiphubapps.ax.data.repository.dataSource.UserLocalDataSource
import com.tiphubapps.ax.data.entity.UserEntity
import kotlinx.coroutines.flow.Flow
import com.tiphubapps.ax.data.repository.dataSource.Result
import com.tiphubapps.ax.data.repository.dataSource.Result.Success
import com.tiphubapps.ax.data.repository.dataSource.Result.Error
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf


//TODO: Should probably be faked at REPOSITORY LEVEL?
// Nope, this is intended to TEST the REPOSITORY LEVEL.
class FakeLocalDataSource(var users: MutableList<UserEntity> = mutableListOf())  : UserLocalDataSource {
    private val flow = MutableSharedFlow<Int>()
    suspend fun emit(value: Int) = flow { emit(value) }
    override fun getUsersFromDB(userId: Int): Flow<UserEntity?> {
        println(users)
        val fon = users.map{
                            println(it.id);
                            it
                        }
                        .firstOrNull{it.id == userId}
                        .also{println("$userId $it ${it?.id}")}

        println("$fon")

        return flowOf(users.firstOrNull{it.id == userId})
    }

    suspend fun getUsers(): Result<List<UserEntity>> {
        users?.let { return Success(ArrayList(it)) }
        return Error(
            Exception("Tasks not found")
        )
    }


    suspend fun deleteAllUsers() {
        users?.clear()
    }

    suspend fun saveUser(user: UserEntity) {
        users?.add(user)
    }


}