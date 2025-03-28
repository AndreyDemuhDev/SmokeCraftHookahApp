package com.pidzama.smokecrafthookahapp.data.network.token_service

import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

//сетевой перехватчик для access токена
//этот перехватчик будет использоваться для предоставления токена доступа
class AccessTokenInterceptor @Inject constructor(
    private val tokenManager: JwtTokenManager,
) : Interceptor {
    companion object {
        const val HEADER_AUTHORIZATION = "Authorization"
        const val TOKEN_TYPE = "Bearer"
    }
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking {
            tokenManager.getAccessJwt()
        }
        val request = chain.request().newBuilder()
        request.addHeader(HEADER_AUTHORIZATION, "$TOKEN_TYPE $token")
        return chain.proceed(request.build())
    }
}