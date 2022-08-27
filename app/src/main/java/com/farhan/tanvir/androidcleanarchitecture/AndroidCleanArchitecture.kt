package com.farhan.tanvir.androidcleanarchitecture
import android.app.Application
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
}