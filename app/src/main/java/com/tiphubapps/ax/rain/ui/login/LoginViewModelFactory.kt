package com.tiphubapps.ax.rain.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tiphubapps.ax.rain.data.LoginDataSource
import com.tiphubapps.ax.rain.data.LoginRepository
import com.tiphubapps.ax.data.api.UserApi
import com.tiphubapps.ax.data.db.UserDB
import com.tiphubapps.ax.data.db.UserDao
import javax.inject.Inject

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class LoginViewModelFactory @Inject constructor(
    val userDao: UserDao,
    val userApi: UserApi,
    val userDB: UserDB
) : ViewModelProvider.Factory{

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(
                loginRepository = LoginRepository(
                    dataSource = LoginDataSource(
                        /*userUseCases = UserUseCases(
                            getAllUsersUseCase = GetAllUsersUseCase(
                                userRepository = UserRepositoryImpl(
                                    userLocalDataSource = UserLocalDataSourceImpl(
                                        userDao = userDao
                                    ),
                                    userRemoteDataSource = UserRemoteDataSourceImpl(
                                        userApi = userApi,
                                        userDB = userDB
                                    )
                                )
                            ),
                                    getUsersFromDBUseCase = GetUsersFromDBUseCase(
                                    userRepository = UserRepositoryImpl(
                                        userLocalDataSource = UserLocalDataSourceImpl(
                                            userDao = userDao
                                        ),
                                        userRemoteDataSource = UserRemoteDataSourceImpl(
                                            userApi = userApi,
                                            userDB = userDB
                                        )
                                    )
                                    ),
                            postLoginUseCase = PostLoginUseCase(
                                userRepository = UserRepositoryImpl(
                                    userLocalDataSource = UserLocalDataSourceImpl(
                                        userDao = userDao
                                    ),
                                    userRemoteDataSource = UserRemoteDataSourceImpl(
                                        userApi = userApi,
                                        userDB = userDB
                                    )
                                )
                            )
                        ) */
                    ),
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}