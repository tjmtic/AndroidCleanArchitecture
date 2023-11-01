package com.tiphubapps.ax.data.api

import com.google.gson.annotations.SerializedName

data class CreateSessionRequest (
    @SerializedName("receiver")
    var receiver: String,

)