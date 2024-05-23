package com.pidzama.smokecrafthookahapp.data.network

import android.util.Log
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

//сетевой перехватчик для refresh токена
//Этот перехватчик будет использоваться для предоставления токена обновления
class RefreshTokenInterceptor @Inject constructor(
    private val tokenManager: JwtTokenManager,
) : Interceptor {
    companion object {
        const val HEADER_AUTHORIZATION = "Authorization"
        const val TOKEN_TYPE = "Bearer"
    }
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking {
            tokenManager.getRefreshJwt()
        }
        val request = chain.request().newBuilder()
        request.addHeader(HEADER_AUTHORIZATION, "$TOKEN_TYPE $token")
        Log.d("MyLog", "RefreshTokenInterceptor TOKEN = $token")
        return chain.proceed(request.build())
    }
}