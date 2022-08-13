package com.farhan.tanvir.data.api

import com.farhan.tanvir.domain.model.TipSession
import com.farhan.tanvir.domain.model.User
import com.farhan.tanvir.domain.model.UserList
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.GET

interface UserApi {
    @GET("api/get/user")
    suspend fun getCurrentUser(): Response<User>

    @GET("api/get/user")
    suspend fun getAllUsers(): Response<UserList>

    @GET("api/get/sender/sessions")
    suspend fun getSenderTipSessions(): Response<List<TipSession>>

    @GET("api/get/receiver/sessions")
    suspend fun getReceiverTipSessions(): Response<List<TipSession>>

    @GET("api/get/delete/history")
    suspend fun getDeleteAllHistory(): Response<JSONObject>

    @GET("api/get/delete/contributors")
    suspend fun getDeleteAllContributors(): Response<JSONObject>
}