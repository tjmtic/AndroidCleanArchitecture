package com.farhan.tanvir.androidcleanarchitecture.data

import android.util.Log
import com.farhan.tanvir.domain.model.User
import java.io.IOException
import javax.inject.Inject

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource @Inject constructor(
   // val userUseCases: UserUseCases
) {

    suspend fun login(username: String, password: String): Result<String> {
        try {
            Log.d("TIME123", "ACtual;ly loging in..." + username + password)
            // TODO: handle loggedInUser authentication
          //  val realUser = userUseCases.postLoginUseCase(username,password);
           // var token = realUser?.getString("token");
         /*   Log.d("TIME123", "ACtual;ly loging in.222.." + realUser)

            realUser?.let{
                var token = it.get("token");
                return Result.Success(token.asString)
            }*/
            Log.d("TIME123", "ACtual;ly loging in.3333..")

            //val fakeUser = LoggedInUser(java.util.UUID.randomUUID().toString(), "Jane Doe")
            val fakeUser = User(0,
                1,
                "5e22b8a4bf397f08932de490",
                3372,
                0,
                0,"\"62777c22b60ca7002878aaee\",\"623fd0010574c6002926b5f7\",\"5e22b920bf397f08932de492\"",
                "5e22b920bf397f08932de492",
                "5e6ece3d7d914a002496a86c",
                "5e6ece3d7d914a002496a86c",
                "5e6ece3d7d914a002496a86c",
                106,
                "jay@0260tech.com",
                "Jay",
                "sb-lau0a5550231@personal.example.com",
                "Ci3-mOUhcIMXC_cUAADX",
                null,
                "sb-khu2t8374628@personal.example.com",
            "https://tip-hub.s3.amazonaws.com/users/img/5e22b8a4bf397f08932de490-profile.png",
                "https://tip-hub.s3.amazonaws.com/users/img/5e22b8a4bf397f08932de490-profile.png",
                "https://tip-hub.s3.amazonaws.com/users/img/5e22b8a4bf397f08932de490-profile.png")

            return Result.Error(Exception("No Token Found"))
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}