package com.tiphubapps.ax.data.api

import com.google.gson.annotations.SerializedName

data class LoginRequest (
    @SerializedName("username")
    var username: String,

    @SerializedName("password")
    var password: String
)