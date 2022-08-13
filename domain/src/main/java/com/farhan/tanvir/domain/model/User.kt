package com.farhan.tanvir.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    var pk: Long = 0,
    @SerializedName("id")
    val userId: Int,
    @SerializedName("payerBalance")
    val payerBalance: Int,
    @SerializedName("receiverBalance")
    val receiverBalance: Int,
    @SerializedName("available")
    val available: Int,
    @SerializedName("contacts")
    val contacts: String,
    @SerializedName("favorites")
    val favorites: String,
    @SerializedName("history")
    val history: String,
    @SerializedName("contributors")
    val contributors: String,
    @SerializedName("sponsors")
    val sponsors: String,
    @SerializedName("balance")
    val balance: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("paypal")
    val paypal: String?,
    @SerializedName("socketId")
    val socketId: String?,
    @SerializedName("firebaseDeviceToken")
    val firebaseDeviceToken: String?,
    @SerializedName("payerEmail")
    val payerEmail: String?,
    @SerializedName("images")
    val images: String?
) : Serializable