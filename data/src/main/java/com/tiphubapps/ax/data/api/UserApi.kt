package com.tiphubapps.ax.data.api

import com.tiphubapps.ax.domain.model.TipSession
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.*

interface UserApi {

    //////GET////////
    @GET("api/get/user")
    suspend fun getCurrentUser(): Response<JsonObject>

    @GET("api/get/sender/sessions")
    suspend fun getSenderTipSessions(): Response<List<TipSession>>

    @GET("api/get/receiver/sessions")
    suspend fun getReceiverTipSessions(): Response<List<TipSession>>

    @GET("api/get/users")
    suspend fun getAllUsers(): Response<JsonArray>

    @GET("api/get/delete/history")
    suspend fun getDeleteAllHistory(): Response<JsonObject>

    @GET("api/get/delete/contributors")
    suspend fun getDeleteAllContributors(): Response<JsonObject>

    @GET("api/stripe/account/{accountId}")
    suspend fun getStripeAccountInfo(@Path("accountId") id: String): Response<JsonObject>

    @GET("api/stripe/link/{accountId}")
    suspend fun getStripeDashboardLink(@Path("accountId") id: String): Response<JsonObject>
    //////////////////

    ///////POST///////
    @POST("api/create/session")
    suspend fun createSessionByUser(@Body request: CreateSessionRequest): Response<JsonObject>

    @POST("api/put/payer/email")
    suspend fun putPaypalEmail(@Body request: EmailRequest): Response<JsonObject>

    @POST("api/get/users/id")
    suspend fun getUsersByIds(@Body request: GetUsersRequest): Response<JsonObject>
    @POST("api/put/contact")
    suspend fun putContact(@Body request: ContactRequest): Response<JsonObject>
    @POST("api/delete/contact")
    suspend fun deleteContact(@Body request: ContactRequest): Response<JsonObject>

    @POST("api/get/user/id")
    suspend fun getUserById(@Body request: IdRequest): Response<JsonObject>

    @POST("api/get/tip/session/sender/id")
    suspend fun getSenderSession(@Body request: ReceiverRequest): Response<JsonObject>
    @POST("api/get/tip/session/receiver/id")
    suspend fun getReceiverSession(@Body request: SenderRequest): Response<JsonObject>

    @POST("api/put/sponsor")
    suspend fun putSponsor(@Body request: SponsorRequest): Response<JsonObject>
    @POST("api/delete/sponsor")
    suspend fun deleteSponsor(@Body request: SponsorRequest): Response<JsonObject>

    @POST("api/put/favorite")
    suspend fun putFavorite(@Body request: FavoriteRequest): Response<JsonObject>
    @POST("api/delete/favorite")
    suspend fun deleteFavorite(@Body request: FavoriteRequest): Response<JsonObject>

    @POST("api/login")
    suspend fun postLogin(
        @Body request: LoginRequest
    ): Response<JsonObject>

    @POST("api/verify/phone")
    suspend fun postVerifySignup(
        @Body request: VerificationRequest
    ): Response<JsonObject>

    @POST("api/send/verification")
    suspend fun postSendAuthCode(
        @Body request: UsernameRequest
    ): Response<JsonObject>

    @POST("api/logout")
    suspend fun postLogout(): Response<JsonObject>

    @POST("api/start/payout")
    suspend fun postStartPayout(): Response<JsonObject>

    @POST("api/new/post")
    suspend fun postSocialUrl(
        @Body request: UrlRequest
    ): Response<JsonObject>

    @POST("api/new/post")
    suspend fun testPostSocialUrl(
        @Body request: SocialPostRequest
    ): Response<JsonObject>
}