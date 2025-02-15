package com.sani.corporation.travellers.util.network

import com.sani.corporation.travellers.util.Async

sealed class AsyncState<out T> {
    object Loading : AsyncState<Nothing>()

    data class Error(val errorMessage: String) : AsyncState<Nothing>()

    data class Success<out T>(val data: T) : AsyncState<T>()

    object SuccessEmpty : AsyncState<Nothing>()
}