package com.pidzama.smokecrafthookahapp.data.network

import com.pidzama.smokecrafthookahapp.data.model.RandomRecipeSubList
import com.pidzama.smokecrafthookahapp.data.model.RandomRecipeSubListItem
import com.pidzama.smokecrafthookahapp.data.remote.authorization.AuthRequest
import com.pidzama.smokecrafthookahapp.data.remote.authorization.AuthResponse
import com.pidzama.smokecrafthookahapp.data.remote.reduce.ReduceRecipeResponse
import com.pidzama.smokecrafthookahapp.presentation.auth.common.AuthToken
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface SmokeCraftApi {

//    @POST("api/v1/auth/token/login")
//    suspend fun loginUser(
//        @Body loginRequest: AuthRequest
//    ): AuthResponse

    @POST("api/v1/auth/jwt/create")
    suspend fun getJwtTokenUser(
        @Body loginRequest: AuthRequest
    ): AuthToken

    @GET("api/v1/storage/tobacco")
    suspend fun getAllTobaccosList(
        @Header("Authorization") token: String
    ): Response<List<RandomRecipeSubListItem>>

//    api/v1/storage/tobacco/get_random_recipes/
    @GET("api/v1/generator/")
    suspend fun getRandomGenerateRecipeList(
        @Header("Authorization") token: String
    ): Response<List<RandomRecipeSubList>>

    @POST("api/v1/storage/reduce_tobacco/")
    suspend fun reduceRecipe(
        @Body recipe: RandomRecipeSubList
    ): Response<ReduceRecipeResponse>
}