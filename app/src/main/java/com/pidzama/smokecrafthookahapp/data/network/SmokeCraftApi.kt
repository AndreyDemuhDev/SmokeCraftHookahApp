package com.pidzama.smokecrafthookahapp.data.network

import com.pidzama.smokecrafthookahapp.data.model.RandomRecipeSubList
import com.pidzama.smokecrafthookahapp.data.remote.AuthRequest
import com.pidzama.smokecrafthookahapp.data.remote.AuthResponse
import com.pidzama.smokecrafthookahapp.data.remote.reduce.ReduceRecipeRequest
import com.pidzama.smokecrafthookahapp.data.remote.reduce.ReduceRecipeResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface SmokeCraftApi {

    @POST("api/v1/auth/token/login")
    suspend fun loginUser(
        @Body loginRequest: AuthRequest
    ): AuthResponse

    @GET("api/v1/generator/get_random_recipes/")
    suspend fun getRandomGenerateRecipeList(): Response<List<RandomRecipeSubList>>

    @POST("api/v1/storage/reduce_tobacco/")
    suspend fun reduceRecipe(
        @Body recipe: RandomRecipeSubList
    ): Response<ReduceRecipeResponse>
}