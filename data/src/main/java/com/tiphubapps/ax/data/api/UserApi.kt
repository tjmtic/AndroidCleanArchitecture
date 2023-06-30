package com.tiphubapps.ax.data.api

import com.tiphubapps.ax.domain.model.TipSession
import com.tiphubapps.ax.domain.model.User
import com.tiphubapps.ax.domain.model.UserList
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.*

interface UserApi {
    @GET("api/get/user")
    suspend fun getCurrentUser(@HeaderMap authedHeaders: AuthenticatedHeaders): Response<JsonObject>

    @POST("api/get/user/id")
    suspend fun getUserById(@Body id: JsonObject, @HeaderMap authedHeaders: AuthenticatedHeaders): Response<JsonObject>

    @POST("api/get/users/id")
    suspend fun getUsersByIds(@Body history: JsonObject, @HeaderMap authedHeaders: AuthenticatedHeaders): Response<JsonObject>

    @GET("api/get/users")
    suspend fun getAllUsers(@HeaderMap authedHeaders: AuthenticatedHeaders): Response<JsonArray>

    @GET("api/get/sender/sessions")
    suspend fun getSenderTipSessions(): Response<List<TipSession>>

    @GET("api/get/receiver/sessions")
    suspend fun getReceiverTipSessions(): Response<List<TipSession>>

    @POST("api/create/session")
    suspend fun createSessionByUser(@Body data: JsonObject, @HeaderMap authedHeaders: AuthenticatedHeaders): Response<JsonObject>

    @GET("api/get/delete/history")
    suspend fun getDeleteAllHistory(): Response<JsonObject>

    @GET("api/get/delete/contributors")
    suspend fun getDeleteAllContributors(): Response<JsonObject>


    @POST("api/login")
    suspend fun postLogin(
        @Body request: LoginRequest
    ): Response<JsonObject>

    @POST("api/logout")
    suspend fun postLogout(): Response<JsonObject>
}