package com.tiphubapps.ax.data.api

import com.google.gson.annotations.SerializedName

data class GetUsersRequest (
    @SerializedName("history")
    var history: String,

    @SerializedName("contributors")
    var contributors: String
)