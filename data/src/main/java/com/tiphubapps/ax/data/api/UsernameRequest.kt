package com.tiphubapps.ax.data.api

import com.google.gson.annotations.SerializedName

data class UsernameRequest (
    @SerializedName("username")
    var username: String,

)