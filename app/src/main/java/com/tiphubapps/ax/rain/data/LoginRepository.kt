package com.tiphubapps.ax.rain.data

import com.tiphubapps.ax.domain.model.User
import javax.inject.Inject

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginRepository @Inject constructor(val dataSource: LoginDataSource) {

    // in-memory cache of the loggedInUser object
    var user: User? = null
        private set
    var token: String? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        user = null
    }

    fun logout() {
        user = null
        dataSource.logout()
    }

    suspend fun login(username: String, password: String): Result<String> {
        // handle login
        val result = dataSource.login(username, password)
        //val result = userUseCases.postLoginUseCase.invoke(username, password)

        if (result is Result.Success) {
            setLoggedInUser(result.data)
        }

        return result
    }

    private fun setLoggedInUser(loggedInUserToken: String) {
        this.token = loggedInUserToken
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore

        //set application token
    }
}