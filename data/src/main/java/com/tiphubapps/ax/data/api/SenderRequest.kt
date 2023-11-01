package com.tiphubapps.ax.data.api

import com.google.gson.annotations.SerializedName

data class SenderRequest (
    @SerializedName("sender")
    var sender: String,

)