package com.tiphubapps.ax.data.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi

interface CoroutineContextProvider {
    val main: CoroutineDispatcher
    val default: CoroutineDispatcher
    val io: CoroutineDispatcher
    val exceptionHandler: CoroutineExceptionHandler
}

val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
    val errorMessage = throwable.message ?: "An error occurred"
    //Log.e(TAG, errorMessage)
    throwable.printStackTrace()
}

class MainCoroutineContextProvider : CoroutineContextProvider {
    override val main: CoroutineDispatcher = Dispatchers.Main
    override val default: CoroutineDispatcher = Dispatchers.Default
    override val io: CoroutineDispatcher = Dispatchers.IO
    override val exceptionHandler: CoroutineExceptionHandler = coroutineExceptionHandler
}

@ExperimentalCoroutinesApi
class TestCoroutineContextProvider : CoroutineContextProvider {
    override val main: CoroutineDispatcher = Dispatchers.Unconfined
    override val default: CoroutineDispatcher = Dispatchers.Unconfined
    override val io: CoroutineDispatcher = Dispatchers.Unconfined
    override val exceptionHandler: CoroutineExceptionHandler = coroutineExceptionHandler
}