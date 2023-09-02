package fakes

import androidx.lifecycle.MutableLiveData
import com.tiphubapps.ax.domain.model.User
import com.tiphubapps.ax.domain.repository.DefaultUsersRepository
import com.tiphubapps.ax.domain.repository.UseCaseResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

class FakeDefaultUsersRepository : DefaultUsersRepository {

    var userServiceData: LinkedHashMap<String, User> = LinkedHashMap()
    private val observableUsers = MutableStateFlow<UseCaseResult<List<User>>>(UseCaseResult.Loading)

    override fun observeUsers(): Flow<UseCaseResult<List<User>>> {
        runBlocking { refreshUsers() }
        return observableUsers
    }

    override suspend fun getUsers(forceUpdate: Boolean): UseCaseResult<List<User>> {
        return UseCaseResult.UseCaseSuccess(userServiceData.values.toList())
    }

    override suspend fun refreshUsers() {
        observableUsers.value = getUsers(true)
    }

    override fun observeUser(userId: String): Flow<UseCaseResult<User>> {
        return observableUsers.map { users ->
            when (users) {
                is UseCaseResult.Loading -> UseCaseResult.Loading
                is UseCaseResult.UseCaseError -> UseCaseResult.UseCaseError(users.exception)
                is UseCaseResult.UseCaseSuccess -> {
                    val user = users.data.firstOrNull() { it.userId == userId }
                        ?: return@map UseCaseResult.UseCaseError(Exception("Not found"))
                    UseCaseResult.UseCaseSuccess(user)
                }
            }
        }
    }

    override suspend fun getUser(userId: String, forceUpdate: Boolean): UseCaseResult<User> {

        userServiceData.values.firstOrNull{ it.userId === userId }?.let{
            return UseCaseResult.UseCaseSuccess(it)
        }

        return UseCaseResult.UseCaseError(Exception("Empty Error"))

    }

    override suspend fun refreshUser(userId: String) {
        refreshUsers()
    }

    override suspend fun saveUser(user: User) {
        userServiceData.plus(Pair(user.userId,user))
    }

    override suspend fun completeUser(user: User) {
        TODO("Not yet implemented")
    }

    override suspend fun completeUser(userId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun activateUser(user: User) {
        TODO("Not yet implemented")
    }

    override suspend fun activateUser(userId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun clearCompletedUsers() {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllUsers() {
        userServiceData.clear()
    }

    override suspend fun deleteUser(userId: String) {
        userServiceData.remove(userId)
    }
}