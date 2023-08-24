package com.tiphubapps.ax.domain.repository

import android.content.Context
import android.widget.Toast

//import javax.inject.Inject

class AndroidFrameworkRepository constructor(private val context: Context) {
    // Use 'context' for Android-specific operations

    fun showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
        context.let {
            Toast.makeText(it, message, duration).show()
        }
    }
}
