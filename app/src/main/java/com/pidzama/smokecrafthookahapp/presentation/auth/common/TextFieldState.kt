package com.pidzama.smokecrafthookahapp.presentation.auth.common

//состояние текстового поля при авторизации
data class TextFieldState(
    val text :  String = "",
    var error: String? = null
)