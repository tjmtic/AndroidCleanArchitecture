package com.abyxcz.disneycodechallenge.domain.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UserList(
    @SerializedName("page")
    val page: Int = 1,
    @SerializedName("results")
    val users: List<User>,
) : Serializable