package com.tiphubapps.ax.data.api

import com.google.gson.annotations.SerializedName

data class VerificationRequest (
    @SerializedName("username")
    var username: String,

    @SerializedName("verificationCode")
    var verificationCode: String
)