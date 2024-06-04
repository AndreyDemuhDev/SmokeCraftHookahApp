package com.pidzama.smokecrafthookahapp.data.network

import android.util.Log
import com.pidzama.smokecrafthookahapp.data.repository.JwtTokenDataStore
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

//аутентификатор который управляет нащими запросами на авторизацию
class AuthAuthenticator @Inject constructor(
    private val tokenManager: JwtTokenManager,
    private val jwtTokenManager: JwtTokenDataStore,
    private val refreshTokenService: RefreshTokenService
) : Authenticator {
    companion object {
        const val HEADER_AUTHORIZATION = "Authorization"
        const val TOKEN_TYPE = "Bearer"
    }

    override fun authenticate(route: Route?, response: Response): Request? {
        val currentToken = runBlocking {
            tokenManager.getAccessJwt()
        }
        synchronized(this) {
            val updatedToken = runBlocking {
                tokenManager.getAccessJwt()
            }
            val refreshToken = runBlocking {
                jwtTokenManager.getRefreshJwt()
            }
            val token = if (currentToken != updatedToken) updatedToken else refreshToken
            Log.d("MyLog", "Отрабатывает authenticate токен = $token")
            return if (token != null) response.request.newBuilder()
                .header(HEADER_AUTHORIZATION, "$TOKEN_TYPE $token")
                .build() else null
        }
    }
}