package com.tiphubapps.ax.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.JsonArray
import com.google.gson.annotations.SerializedName
import com.tiphubapps.ax.data.db.Converters
import com.tiphubapps.ax.data.db.ListTypeConverter
import java.io.Serializable

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    var pk: Long = 0,
    @SerializedName("_id")
    val id: String,
    @SerializedName("id")
    val userId: String?,
    @SerializedName("from")
    val from: String?,
    @SerializedName("payerBalance")
    val payerBalance: Int?,
    @SerializedName("receiverBalance")
    val receiverBalance: Int?,
    @SerializedName("available")
    val available: Int?,
    @SerializedName("contacts")
    @TypeConverters(ListTypeConverter::class)
    val contacts: JsonArray?,
    @SerializedName("favorites")
    val favorites: JsonArray?,
    @SerializedName("history")
    val history: JsonArray?,
    @SerializedName("contributors")
    val contributors: JsonArray?,
    @SerializedName("sponsors")
    val sponsors: JsonArray?,
   // @SerializedName("balance")
   // val balance: Int?,
   // @SerializedName("email")
    //val email: String?,
    @SerializedName("name")
    val name: String?,
    //@SerializedName("paypal")
    //val paypal: String?,
    @SerializedName("socketId")
    val socketId: String?,
    @SerializedName("firebaseDeviceToken")
    val firebaseDeviceToken: String?,
    @SerializedName("payerEmail")
    val payerEmail: String?,
    @SerializedName("images")
    val images: JsonArray?,
    @SerializedName("coverImage")
    val coverImage: String?,
    @SerializedName("profileImage")
    val profileImage: String?,
    @SerializedName("wsSocketId")
    val wsSocketId: String?,
    @SerializedName("onboardingLink")
    val onboardingLink: String?,
    @SerializedName("username")
    val username: String?,
    @SerializedName("verificationCode")
    val verificationCode: String?,
    @SerializedName("verified")
    val verified: Boolean?,
    @SerializedName("stripeId")
    val stripeId: String?,
    @SerializedName("appVersion")
    val appVersion: String?,
    @SerializedName("transferBalance")
    val transferBalance: Int?,
    @SerializedName("paymentFlagged")
    val paymentFlagged: Boolean?,
    @SerializedName("oboarded")
    val onboarded: Boolean?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("updatedAt")
    val updatedAt: String?,
    @SerializedName("socialLinks")
    val socialLinks: String?,
) : Serializable