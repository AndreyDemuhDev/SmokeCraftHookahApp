package com.pidzama.smokecrafthookahapp.presentation.auth.common

sealed class StatusAuth<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : StatusAuth<T>(data)
    class Loading<T>(data: T? = null) : StatusAuth<T>(data)
    class Error<T>(message: String, data: T? = null) : StatusAuth<T>(data, message)
}