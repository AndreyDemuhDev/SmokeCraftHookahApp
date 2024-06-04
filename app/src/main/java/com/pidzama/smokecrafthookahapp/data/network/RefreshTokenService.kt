package com.pidzama.smokecrafthookahapp.data.network

import com.pidzama.smokecrafthookahapp.presentation.auth.common.AuthToken
import com.pidzama.smokecrafthookahapp.presentation.auth.common.RefreshToken
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RefreshTokenService {

    @POST("api/v1/auth/jwt/refresh")
    suspend fun refreshToken(
        @Body refresh: String
    ): Response<RefreshToken>
}