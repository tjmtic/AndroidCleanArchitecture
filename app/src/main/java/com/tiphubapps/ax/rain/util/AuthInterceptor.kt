package com.tiphubapps.ax.rain.util

import android.se.omapi.Session
import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

/**
 * Interceptor to add auth token to requests
 */
class AuthInterceptor @Inject constructor(private val sessionManager: SessionManager) : Interceptor {
    //private val sessionManager = SessionManager(context)
    //val sessionManager1 = sessionManager

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        Log.d("TIME123", "Build Request Start.")
        // If token has been saved, add it to the request
        //val token = application.currentUserToken
        //val token = "eyJraWQiOiJ1WnFRMFlBQk45N0VUY0JWZ3NBR2N0NFdab0cxVzhuQThtUWhXSFV3R0NrPSIsImFsZyI6IlJTMjU2In0.eyJzdWIiOiJjMmUyY2U5Yi0yYTRmLTQ3ZmYtYWFkYS1kZWIyY2M2MDkxNDgiLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsImlzcyI6Imh0dHBzOlwvXC9jb2duaXRvLWlkcC51cy1lYXN0LTEuYW1hem9uYXdzLmNvbVwvdXMtZWFzdC0xX2xXb0lZRWJwZyIsImNvZ25pdG86dXNlcm5hbWUiOiJjMmUyY2U5Yi0yYTRmLTQ3ZmYtYWFkYS1kZWIyY2M2MDkxNDgiLCJhdWQiOiIzaTBtZDZrM2s0OTkyNWY2ZWsxYmRyNTFlYiIsImV2ZW50X2lkIjoiOTcwNDhmZTYtOWUxZC00ZGU2LThiODQtMTBhNThlZGJkODZlIiwidG9rZW5fdXNlIjoiaWQiLCJhdXRoX3RpbWUiOjE2NTMzNjQ0MzAsIm5hbWUiOiJKYXkiLCJleHAiOjE2NTMzNjgwMzAsImlhdCI6MTY1MzM2NDQzMCwiZW1haWwiOiJqYXlAMDI2MHRlY2guY29tIn0.ifBm_T5Ypb8KYnkzktBrbrLumm271GDqwrf5bQrhot_qEGbt1q8TJnZ0w5ALMxqnmhR2uVA4sWogb0_85m3YBEmxXZpd5tp0dg5q9W7YDGc0jce6va2fqPtXpUbis0LF-Dfv9XEQ8kFXQqIh0JQueAe3n6VPhKMuOixx1mpjtsN1VHO3J-2P7KukPwwuQqR-6GElKEmKlLLp95_s3jJV5nuc3lkZPGq2mzA172Ud3NAGs8_cLcU1NLq_2YuxwHpHm4FIVJhwI13T3udOEowc_vCEMAyh6ad13ibc--YofCiVVwAYlXcInpF_a4oTDUgnUtIrp0FLVyuJse94BHsLng"
        sessionManager.getEncryptedPreferencesValue("userToken")?.let {
            Log.d("TIME123", "Got Encrpted preferences token token ${it}")
            requestBuilder.addHeader("Authorization", "Bearer $it")
        }

        Log.d("TIME123", "Build Request END.")

       // requestBuilder.addHeader("Content-Type", "application/x-www-form-urlencoded")
        requestBuilder.addHeader("Accept", "application/json")

        //requestBuilder.addHeader("x-api-device", "123")
        //requestBuilder.addHeader("x-api-device-type", "android")
        //requestBuilder.addHeader("x-notification-token", "123")

        return chain.proceed(requestBuilder.build())
    }
}