package com.farhan.tanvir.androidcleanarchitecture

import android.R
import android.app.Application
import android.graphics.Bitmap
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class AndroidCleanArchitecture : Application(){
    var currentUserToken : String? = null
        get set

    var currentUserSocketId : String? = null
        get set

    fun logout(){
        currentUserToken = null
        currentUserSocketId = null
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
}