package com.tiphubapps.ax.data.api

import com.google.gson.annotations.SerializedName

data class EmailRequest (
    @SerializedName("email")
    var email: String,

)