package test1

import androidx.paging.RemoteMediator
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.tiphubapps.ax.domain.model.User
import com.tiphubapps.ax.domain.repository.UseCaseResult
import com.tiphubapps.ax.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map

class FakeUserRepository() : UserRepository {
    ////FROM IMPL
    //In-Memory Cache
    var token: String?;
    var currentUser : String?;
    // MutableStateFlow to hold and update the local value
    private val _localValue = MutableStateFlow("Initial Value")
    // Expose the value as a StateFlow (immutable view)
    val localValue: StateFlow<String> = _localValue
    init {
        currentUser = null;
        token = null;

        //add fake users.....

    }
    val isLoggedIn: Boolean
        get() = currentUser != null

    var userServiceData: LinkedHashMap<String, User> = LinkedHashMap()
    private val observableUsers = MutableStateFlow<UseCaseResult<List<User>>>(UseCaseResult.Loading)

    fun addUser(user : User){
        userServiceData.plus(Pair(user.userId, user))
    }

    override fun getCurrentToken(): String? {
        return token
    }

    override suspend fun getCurrentUser(): UseCaseResult<User> {
        userServiceData.filter { it.value.userId === currentUser }.also{ it ->
            it.firstNotNullOfOrNull { return UseCaseResult.UseCaseSuccess(it.value) }
        }

        return UseCaseResult.UseCaseError(Exception("Empty Error"))

    }

    override suspend fun getCurrentUserWithToken(token: String): UseCaseResult<User> {
        userServiceData.filter { it.value.userId === currentUser }.also{ it ->
            it.firstNotNullOfOrNull { return UseCaseResult.UseCaseSuccess(it.value) }
        }

        return UseCaseResult.UseCaseError(Exception("Empty Error"))
    }

    override suspend fun getUserById(id: String, token: String): UseCaseResult<User> {
        userServiceData.filter { it.value.userId === id }.also{ it ->
            it.firstNotNullOfOrNull { return UseCaseResult.UseCaseSuccess(it.value) }
        }

        return UseCaseResult.UseCaseError(Exception("Empty Error"))
    }

    override suspend fun getUsersById(
        historyIds: JsonArray,
        contributorIds: JsonArray,
        token: String
    ): UseCaseResult<List<User>> {

        val hids = historyIds.map { it.asString }
        val cids = contributorIds.map { it.asString }

        userServiceData.filter { hids.contains(it.value.userId) || cids.contains(it.value.userId) }.also{ it ->

            if (it.isEmpty()) { return UseCaseResult.UseCaseError(Exception("Empty Error")) }

            val li = it.map { it.value }
            return UseCaseResult.UseCaseSuccess(li)
        }
    }

    override suspend fun createSessionByUsers(data: JsonObject, token: String): JsonObject? {

        return JsonObject().apply{
            this.addProperty("_id", "1")
            this.addProperty("previous", "-1")
        }
    }

    override suspend fun getAllUsers(): UseCaseResult<List<User>> {
        return UseCaseResult.UseCaseSuccess(userServiceData.values.toList())
    }

    override suspend fun getAllUsersWithToken(token: String): UseCaseResult<List<User>> {
        return UseCaseResult.UseCaseSuccess(userServiceData.values.toList())
    }

    override fun getUsersFromDB(userId: Int): Flow<User?> {
        return observableUsers.map {
            when(it){
                is UseCaseResult.UseCaseSuccess -> { it.data.firstOrNull{ it.id === userId }}
                else -> null
            }
        }
    }

    override suspend fun postLogin(email: String, password: String): JsonObject? {
        return JsonObject().apply{
            this.addProperty("token", "1")
        }
    }

    override suspend fun logout(): Boolean {
        token = null
        currentUser = null
        
        return true
    }

    override fun getLocalValueFlow(): StateFlow<String> {
        return localValue
    }

    override fun updateLocalValue(value: String) {
        _localValue.value = value
    }
}