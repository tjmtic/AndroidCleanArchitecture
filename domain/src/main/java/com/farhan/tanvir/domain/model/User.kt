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
    @SerializedName("name")
    val name: String,
    @SerializedName("reserved")
    val reserved: Boolean,
) : Serializable