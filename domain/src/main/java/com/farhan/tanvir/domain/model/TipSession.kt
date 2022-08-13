package com.farhan.tanvir.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "tip_sessions")
data class TipSession(
    @PrimaryKey(autoGenerate = true)
    var pk: Long = 0,
    @SerializedName("_id")
    val userId: Int,
    @SerializedName("currentTipAmount")
    val currentTipAmount: Int,
    @SerializedName("amount")
    val amount: Int,
    @SerializedName("status")
    val status: String,
    @SerializedName("limit")
    val limit: Int,
    @SerializedName("cleared")
    val cleared: Boolean,
    @SerializedName("sender")
    val sender: String,
    @SerializedName("receiver")
    val receiver: String,
    @SerializedName("previous")
    val previous: String,
    @SerializedName("expires")
    val expires: String
) : Serializable