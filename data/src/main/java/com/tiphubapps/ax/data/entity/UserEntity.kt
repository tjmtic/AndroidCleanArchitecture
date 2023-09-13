package com.tiphubapps.ax.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
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




{"socialLinks":{"instagram":null,"tiktok":"https://www.tiktok.com/@jaymaxfpv",
    "facebook":null,"youtube":null,"twitter":null,"threads":null,"linkedin":null,"snapchat":null},

    "wsSocketId":"::ffff:192.168.1.128",
    "onboardingLink":null,
    "_id":"631baec7904d47fa1348c82d",
    "username":12135551212,
    "name":"QeenDancer777",
    "verificationCode":null,
    "verified":true,
    "images":[{"_id":"631bbba4a7d1c92042bc1aef",
    "url":"https://tip-hub.s3.amazonaws.com/users/img/631baec7904d47fa1348c82d-cover.png",
    "name":"631baec7904d47fa1348c82d-cover.png",
    "type":"cover",
    "user":"631baec7904d47fa1348c82d",
    "createdAt":"2022-09-09T22:18:12.151Z",
    "updatedAt":"2022-09-09T22:18:12.151Z","__v":0},

    {"_id":"631bbbaba7d1c92042bc1af5",
        "url":"https://tip-hub.s3.amazonaws.com/users/img/631baec7904d47fa1348c82d-profile.png",
        "name":"631baec7904d47fa1348c82d-profile.png",
        "type":"profile",
        "user":"631baec7904d47fa1348c82d",
        "createdAt":"2022-09-09T22:18:19.387Z",
        "updatedAt":"2022-09-09T22:18:19.387Z","__v":0}],

    "payerBalance":91,
    "receiverBalance":853,
    "available":0,

    "contacts":[],
    "favorites":["5e22b8a4bf397f08932de490","5e22b920bf397f08932de492"],
    "history":["5e22b8a4bf397f08932de490","64cca6cb12e07b0437db0db7"],
    "contributors":["5e22b8a4bf397f08932de490","62777c22b60ca7002878aaee","5e22b920bf397f08932de492"],
    "sponsors":["5e275ea3153b66002465307d","623fd0010574c6002926b5f7"],
    "createdAt":"2022-09-09T21:23:20.394Z",
    "updatedAt":"2023-09-13T23:13:13.985Z",
    "__v":15,
    "firebaseDeviceToken":"c1KgAgee10dRjVOCT462RP:APA91bFT_vaxlJxPnma6gk-NtWR9ytvdTwqU2DvvhJ5tvTp7iKGt-Qc5SXwfk-vnTEsolZmS0p3hkGGqCgKHCT-aKoTeTPtOu5Rnu7LFsg4CzYAY_5IEsGzZPSWKatssmI31iU5oep2A",

    "socketId":null,
    "payerEmail":"oqs29201@zslsz.com",
    "paymentFlagged":true,
    "stripeId":"acct_1NHHYmPDmHtnjlTs",
    "onboarded":true,
    "batches":[{"batchId":"batch_xk0qclhju4","amount":18,"receiver":"631baec7904d47fa1348c82d",
    "status":"pending","stripeId":"acct_1NCB5DPLqKFUjIGF","date":"2023-07-03T15:00:59.892Z"
    ,"_id":"64a2e2ab191468711305da27"},

    {"batchId":"batch_syuikgp0qy","amount":12,"receiver":"631baec7904d47fa1348c82d",
        "status":"pending","stripeId":"acct_1NCB5DPLqKFUjIGF","date":"2023-07-04T15:00:49.106Z",
        "_id":"64a43421191468711305daed"},
    {"batchId":"batch_3y5fp9jnwwf","amount":7,"receiver":"631baec7904d47fa1348c82d","status":"pending","stripeId":"acct_1NCB5DPLqKFUjIGF","date":"2023-07-11T15:01:09.065Z","_id":"64ad6eb5191468711305de5e"},
    {"batchId":"batch_6x4ty2xde9","amount":2,"receiver":"631baec7904d47fa1348c82d","status":"pending","stripeId":"acct_1NCB5DPLqKFUjIGF","date":"2023-07-14T15:00:53.766Z","_id":"64b16325191468711305df4c"}],

    "transferBalance":0,
    "appVersion":"v2",
    "from":"header",
    "id":"631baec7904d47fa1348c82d"}