package com.farhan.tanvir.data.api

import com.farhan.tanvir.domain.model.TipSession
import com.farhan.tanvir.domain.model.User
import com.farhan.tanvir.domain.model.UserList
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserApi {
    @GET("api/get/user")
    suspend fun getCurrentUser(): Response<JsonObject>

    @GET("api/get/users")
    suspend fun getAllUsers(): Response<JsonObject>

    @GET("api/get/sender/sessions")
    suspend fun getSenderTipSessions(): Response<List<TipSession>>

    @GET("api/get/receiver/sessions")
    suspend fun getReceiverTipSessions(): Response<List<TipSession>>

    @GET("api/get/delete/history")
    suspend fun getDeleteAllHistory(): Response<JSONObject>

    @GET("api/get/delete/contributors")
    suspend fun getDeleteAllContributors(): Response<JSONObject>


    @POST("api/login")
    suspend fun postLogin(
        @Body request: LoginRequest
    ): Response<JsonObject>
}