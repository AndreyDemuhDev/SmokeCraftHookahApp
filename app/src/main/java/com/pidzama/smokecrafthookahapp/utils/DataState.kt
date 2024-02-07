package com.pidzama.smokecrafthookahapp.utils

sealed class DataState<out T> {

    data class Success<out R>(val data: R) : DataState<R>()
    data class Failure(val message: Throwable) : DataState<Nothing>()
    object Loading : DataState<Nothing>()

    override fun toString(): String {
        return when(this){
            is Success -> "Success $data"
            is Failure -> "Failure ${message.message}"
            Loading -> "Loading"
        }
    }
}