package com.tiphubapps.ax.data.api

import com.google.gson.annotations.SerializedName

data class SocialPostRequest (
    @SerializedName("url")
    var url: String,
    @SerializedName("token")
    var token: String,
)