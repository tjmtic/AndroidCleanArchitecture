package com.tiphubapps.ax.data.util

import okhttp3.WebSocketListener

interface WebSocketManager {
    suspend fun connect()
    suspend fun disconnect()
    fun sendMessage(message: String)
    fun setListener(listener: WebSocketListener)
    fun clearEvents()
}