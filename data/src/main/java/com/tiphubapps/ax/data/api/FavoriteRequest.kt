package com.tiphubapps.ax.data.api

import com.google.gson.annotations.SerializedName

data class FavoriteRequest (
    @SerializedName("favorite")
    var favorite: String,

)