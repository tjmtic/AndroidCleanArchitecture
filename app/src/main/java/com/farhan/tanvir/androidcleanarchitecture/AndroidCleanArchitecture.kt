package com.farhan.tanvir.androidcleanarchitecture

import android.R
import android.app.Application
import android.graphics.Bitmap
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class AndroidCleanArchitecture : Application(){
    var currentUserToken : String? = null
        get set

    var currentUserSocketId : String = "none"
        get set

    fun logout(){
        currentUserToken = null
        currentUserSocketId = "none"

        setEncryptedPreferences("userToken", "0x0");
    }

    fun generateQR(base: String): Bitmap? {
        try {
            val barcodeEncoder = BarcodeEncoder()
            val bitmap = barcodeEncoder.encodeBitmap("content", BarcodeFormat.QR_CODE, 400, 400)
            //val imageViewQrCode: ImageView = findViewById(R.id.qrCode) as ImageView
            //imageViewQrCode.setImageBitmap(bitmap)
            return bitmap
        } catch (e: Exception) {
            return null
        }
        //return bitmap
    }


    fun setEncryptedPreferences(key: String, value: String){
        val mainKey = MasterKey.Builder(this)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        val sharedPreferences = EncryptedSharedPreferences.create(
            this,
            "TipHubSharPrefEncr",
            mainKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        with (sharedPreferences.edit()) {

            // write all the data entered by the user in SharedPreference and apply
            putString(key, value)
            apply()
        }
    }

    fun getEncryptedPreferencesValue(key: String): String?{
        val mainKey = MasterKey.Builder(this)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        val sharedPreferences = EncryptedSharedPreferences.create(
            this,
            "TipHubSharPrefEncr",
            mainKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        return sharedPreferences.getString(key, "");
    }
}