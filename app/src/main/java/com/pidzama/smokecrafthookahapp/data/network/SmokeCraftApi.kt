package com.pidzama.smokecrafthookahapp.data.network

import com.pidzama.smokecrafthookahapp.data.model.RandomRecipeSubListItem
import com.pidzama.smokecrafthookahapp.data.model.generate_model.ModelRecipeItem
import com.pidzama.smokecrafthookahapp.data.model.orders.OrdersItem
import com.pidzama.smokecrafthookahapp.data.remote.authorization.AuthRequest
import com.pidzama.smokecrafthookahapp.data.remote.reduce.ReduceRecipeRequest
import com.pidzama.smokecrafthookahapp.data.remote.reduce.ReduceRecipeResponse
import com.pidzama.smokecrafthookahapp.presentation.auth.common.AuthToken
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface SmokeCraftApi {

    @POST("api/v1/auth/jwt/create")
    suspend fun getJwtTokenUser(
        @Body loginRequest: AuthRequest
    ): AuthToken

    @GET("api/v1/storage/tobacco")
    suspend fun getAllTobaccosList(
        @Header("Authorization") token: String
    ): Response<List<RandomRecipeSubListItem>>

    @GET("api/v1/random_recept")
    suspend fun getRandomGenerateRecipeList(
        @Header("Token") token: String
    ): Response<List<ModelRecipeItem>>

    @GET("api/v1/reservations")
    suspend fun getOrdersList(
        @Header ("Token") token: String
    ): Response<List<OrdersItem>>

//    @POST("")
//    suspend fun reserveRecipe(
//        @Body loginRequest: AuthRequest
//    ):

    @POST("/api/v1/storage/tobacco-reduce/reduce/")
    suspend fun reduceRecipe(
        @Body recipe: List<ReduceRecipeRequest>
    ): ReduceRecipeResponse
}