package com.pidzama.smokecrafthookahapp.data.repository

import com.pidzama.smokecrafthookahapp.data.DataStoreRepository
import com.pidzama.smokecrafthookahapp.data.model.NewRandomRecipeSubList
import com.pidzama.smokecrafthookahapp.data.model.RandomRecipe
import com.pidzama.smokecrafthookahapp.data.model.RandomRecipeSubList
import com.pidzama.smokecrafthookahapp.data.network.SmokeCraftApi
import com.pidzama.smokecrafthookahapp.data.remote.AuthRequest
import com.pidzama.smokecrafthookahapp.data.remote.reduce.ReduceRecipeResponse
import com.pidzama.smokecrafthookahapp.utils.StatusAuth
import retrofit2.HttpException
import retrofit2.Response
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
            StatusAuth.Success(Unit)
        } catch (e: IOException) {
            StatusAuth.Error("${e.message}")
        } catch (e: HttpException) {
            StatusAuth.Error("${e.message}")
        }
    }


    //списание табака при выборе рецепта
    suspend fun reduceRecipe(recipe: RandomRecipeSubList): Response<ReduceRecipeResponse> {
        return smokeCraftApi.reduceRecipe(recipe)
    }
}