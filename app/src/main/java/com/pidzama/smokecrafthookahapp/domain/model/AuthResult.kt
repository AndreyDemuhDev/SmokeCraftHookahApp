package com.pidzama.smokecrafthookahapp.domain.model

import com.pidzama.smokecrafthookahapp.utils.StatusAuth

data class AuthResult(
    val passwordError: String? = null,
    val emailError : String? = null,
    val result: StatusAuth<Unit>? = null
)