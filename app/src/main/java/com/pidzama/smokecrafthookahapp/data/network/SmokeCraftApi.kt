package com.pidzama.smokecrafthookahapp.data.network

import com.pidzama.smokecrafthookahapp.data.dto.RandomRecipeSubListItem
import com.pidzama.smokecrafthookahapp.data.dto.generate_model.ModelRecipeItem
import com.pidzama.smokecrafthookahapp.data.dto.orders.OrdersItem
import com.pidzama.smokecrafthookahapp.data.remote.authorization.AuthRequest
import com.pidzama.smokecrafthookahapp.data.remote.order.OrderRequest
import com.pidzama.smokecrafthookahapp.data.remote.order.OrderResponse
import com.pidzama.smokecrafthookahapp.data.remote.reduce.ReduceRecipeRequest
import com.pidzama.smokecrafthookahapp.data.remote.reduce.ReduceRecipeResponse
import com.pidzama.smokecrafthookahapp.presentation.auth.common.AuthToken
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface SmokeCraftApi {

    //авторизация
    @POST("api/v1/auth/jwt/create")
    suspend fun getJwtTokenUser(
        @Body loginRequest: AuthRequest
    ): AuthToken

    //списко всех табаков (не используется)
    @GET("api/v1/storage/tobacco")
    suspend fun getAllTobaccosList(
        @Header("Authorization") token: String
    ): Response<List<RandomRecipeSubListItem>>

    //список случайных рецептов
    @GET("api/v1/random_recept")
    suspend fun getRandomGenerateRecipeList(
        @Header("Token") token: String
    ): Response<List<ModelRecipeItem>>

    //списко заказов
    @GET("api/v1/reservations")
    suspend fun getOrdersList(
        @Header("Token") token: String
    ): Response<List<OrdersItem>>

    //списание табака пр формировании заказа
    @POST("/api/v1/storage/tobacco-reduce/reduce/")
    suspend fun reduceRecipe(
        @Body recipe: List<ReduceRecipeRequest>
    ): ReduceRecipeResponse

    //обновление заказа
    @PATCH("/api/v1/reservations/{id}")
    suspend fun updateOrder(
        @Header("Token") token: String,
        @Path(value = "id") id: Int,
        @Body recipes: OrderRequest
    ): OrderResponse

    //оформление заказа
    @POST("/api/v1/reservations/")
    suspend fun createOrder(
        @Body order: OrderRequest
    ): OrderResponse

    //получение информации по заказу
    @GET("/api/v1/reservations/{id}/")
    suspend fun getInfoOrder(
        @Path("id") id: Int
    ): Response<OrderResponse>

    @DELETE("/api/v1/reservations/{id}/")
    suspend fun deleteOrder(
        @Path("id") id: Int
    ): Response<OrderResponse>
}