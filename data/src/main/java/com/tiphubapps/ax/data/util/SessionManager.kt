package com.tiphubapps.ax.data.util

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import javax.inject.Inject
import javax.inject.Named

/**
 * Session manager to save and fetch data from SharedPreferences
 */
class SessionManager @Inject constructor(@Named("shared") sharedPreferences: SharedPreferences, @Named("encrypted") encryptedPreferences: SharedPreferences) {
    private var prefs = sharedPreferences
    private var ePrefs = encryptedPreferences

    companion object {
        const val USER_TOKEN = "user_token"
    }

    /**
     * Function to save auth token
     */
    fun saveAuthToken(token: String) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }

    /**
     * Function to fetch auth token
     */
    fun fetchAuthToken(): String? {
        return prefs.getString(USER_TOKEN, null)
    }



    private fun setEncryptedPreferences(key: String, value: String){

        with (ePrefs.edit()) {

            // write all the data entered by the user in SharedPreference and apply
            putString(key, value)
            apply()
        }
    }

    private fun getEncryptedPreferencesValue(key: String): String?{

        return ePrefs.getString(key, "");
    }

    fun setUserToken(value: String){
        setEncryptedPreferences("userToken", value)
    }
    fun getUserToken(): String?{
        return getEncryptedPreferencesValue("userToken")
    }

    fun clear(){
        setEncryptedPreferences("userToken","")
    }
}