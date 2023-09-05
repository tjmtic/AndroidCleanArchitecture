package com.tiphubapps.ax.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.JsonElement
import com.google.gson.annotations.SerializedName
import java.io.Serializable

//@Entity(tableName = "users")
data class User(
    @SerializedName("pk")
    var pk: Long = 0,
    @SerializedName("_id")
    val id: String,
    @SerializedName("id")
    val userId: String?,
    @SerializedName("payerBalance")
    val payerBalance: Int?,
    @SerializedName("receiverBalance")
    val receiverBalance: Int?,
    @SerializedName("available")
    val available: Int?,
    @SerializedName("contacts")
    val contacts: String?,
    @SerializedName("favorites")
    val favorites: String?,
    @SerializedName("history")
    val history: String?,
    @SerializedName("contributors")
    val contributors: String?,
    @SerializedName("sponsors")
    val sponsors: String?,
    @SerializedName("balance")
    val balance: Int?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("paypal")
    val paypal: String?,
    @SerializedName("socketId")
    val socketId: String?,
    @SerializedName("firebaseDeviceToken")
    val firebaseDeviceToken: String?,
    @SerializedName("payerEmail")
    val payerEmail: String?,
    @SerializedName("images")
    val images: String?,
    @SerializedName("coverImage")
    val coverImage: String?,
    @SerializedName("profileImage")
    val profileImage: String?
) : Serializable