package com.tiphubapps.ax.data.api

import com.google.gson.annotations.SerializedName

data class ReceiverRequest (
    @SerializedName("receiver")
    var receiver: String,

)