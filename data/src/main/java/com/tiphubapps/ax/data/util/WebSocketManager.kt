package com.tiphubapps.ax.data.util

import kotlinx.coroutines.flow.Flow
import okhttp3.WebSocketListener

interface WebSocketManager {
    val isConnected: Flow<Boolean>
    suspend fun connect(): Boolean
    suspend fun disconnect()
    fun sendMessage(message: String)
    fun setListener(listener: WebSocketListener)
    fun setSelectedUserSession(id: String)
    fun setSelectedUserSessionId(id: String)
    fun clearEvents()
}