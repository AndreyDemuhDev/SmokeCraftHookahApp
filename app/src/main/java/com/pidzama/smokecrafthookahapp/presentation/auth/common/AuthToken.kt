package com.pidzama.smokecrafthookahapp.presentation.auth.common

data class AuthToken(
    val refresh: String,
    val access: String
)

data class RefreshToken(
    val access: String
)