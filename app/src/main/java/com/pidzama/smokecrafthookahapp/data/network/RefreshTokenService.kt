package com.pidzama.smokecrafthookahapp.data.network

import com.pidzama.smokecrafthookahapp.data.remote.authorization.RefreshTokenRequest
import com.pidzama.smokecrafthookahapp.data.remote.authorization.RefreshTokenResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RefreshTokenService {

    @POST("api/v1/auth/jwt/refresh")
    suspend fun refreshToken(
        @Body refresh: RefreshTokenRequest
    ): Response<RefreshTokenResponse>
}