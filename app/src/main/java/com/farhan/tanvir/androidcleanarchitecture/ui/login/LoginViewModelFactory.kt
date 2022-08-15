package com.farhan.tanvir.androidcleanarchitecture.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.farhan.tanvir.androidcleanarchitecture.data.LoginDataSource
import com.farhan.tanvir.androidcleanarchitecture.data.LoginRepository
import com.farhan.tanvir.data.api.UserApi
import com.farhan.tanvir.data.db.UserDB
import com.farhan.tanvir.data.db.UserDao
import com.farhan.tanvir.data.repository.UserRepositoryImpl
import com.farhan.tanvir.data.repository.dataSourceImpl.UserLocalDataSourceImpl
import com.farhan.tanvir.data.repository.dataSourceImpl.UserRemoteDataSourceImpl
import com.farhan.tanvir.domain.repository.UserRepository
import com.farhan.tanvir.domain.useCase.GetAllUsersUseCase
import com.farhan.tanvir.domain.useCase.GetUsersFromDBUseCase
import com.farhan.tanvir.domain.useCase.PostLoginUseCase
import com.farhan.tanvir.domain.useCase.UserUseCases
import javax.inject.Inject

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class LoginViewModelFactory @Inject constructor(
    val userDao: UserDao,
    val userApi: UserApi,
val userDB: UserDB) : ViewModelProvider.Factory{

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