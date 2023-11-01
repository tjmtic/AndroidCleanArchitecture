package com.tiphubapps.ax.data.api

import com.google.gson.annotations.SerializedName

data class ContactRequest (
    @SerializedName("contact")
    var contact: String,
)