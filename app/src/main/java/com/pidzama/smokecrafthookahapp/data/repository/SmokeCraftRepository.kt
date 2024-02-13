package com.pidzama.smokecrafthookahapp.data.repository

import com.pidzama.smokecrafthookahapp.data.DataStoreRepository
import com.pidzama.smokecrafthookahapp.data.network.SmokeCraftApi
import com.pidzama.smokecrafthookahapp.data.remote.AuthRequest
import com.pidzama.smokecrafthookahapp.utils.StatusAuth
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SmokeCraftRepository @Inject constructor(
    private val smokeCraftApi: SmokeCraftApi,
    private val preferences: DataStoreRepository
) {

    //авторизация пользователя
    suspend fun loginUser(login: AuthRequest): StatusAuth<Unit> {
        return try {
            val response = smokeCraftApi.loginUser(login)
            preferences.saveAuthToken(response.auth_token)
            preferences.saveUserLogin(login.username)
            StatusAuth.Success(Unit)
        } catch (e: IOException) {
            StatusAuth.Error("${e.message}")
        } catch (e: HttpException) {
            StatusAuth.Error("${e.message}")
        }
    }

}