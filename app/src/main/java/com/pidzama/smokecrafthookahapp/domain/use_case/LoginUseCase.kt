package com.pidzama.smokecrafthookahapp.domain.use_case

import com.pidzama.smokecrafthookahapp.data.remote.AuthRequest
import com.pidzama.smokecrafthookahapp.data.repository.SmokeCraftRepository
import com.pidzama.smokecrafthookahapp.domain.model.AuthResult

class LoginUseCase(
    private val repository: SmokeCraftRepository
) {

    suspend operator fun invoke(
        login: String,
        password: String
    ): AuthResult {

        val loginError = if (login.isBlank()) "Поле Login не может быть пустым" else null
        val passwordError = if (password.isBlank()) "Поле Password не может быть пустым" else null

        if (loginError != null) {
            return AuthResult(
                emailError = loginError
            )
        }

        if (passwordError != null) {
            return AuthResult(
                passwordError = passwordError
            )
        }

        val loginRequest = AuthRequest(
            username = login.trim(),
            password = password.trim()
        )

        return AuthResult(
            result = repository.loginUser(loginRequest)
        )
    }
}