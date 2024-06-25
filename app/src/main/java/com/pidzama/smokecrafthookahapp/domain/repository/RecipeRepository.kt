package com.pidzama.smokecrafthookahapp.domain.repository

import com.pidzama.smokecrafthookahapp.data.dto.RandomRecipeSubListItem
import com.pidzama.smokecrafthookahapp.data.dto.generate_model.ModelRecipeItem
import com.pidzama.smokecrafthookahapp.data.dto.orders.OrdersItem
import com.pidzama.smokecrafthookahapp.data.network.ApiState
import com.pidzama.smokecrafthookahapp.data.remote.authorization.AuthRequest
import com.pidzama.smokecrafthookahapp.data.remote.order.OrderRequest
import com.pidzama.smokecrafthookahapp.data.remote.order.OrderResponse
import com.pidzama.smokecrafthookahapp.data.remote.reduce.ReduceRecipeRequest
import com.pidzama.smokecrafthookahapp.data.remote.reduce.ReduceRecipeResponse
import com.pidzama.smokecrafthookahapp.presentation.auth.common.StatusAuth
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {

    suspend fun loginUser(login: AuthRequest): StatusAuth<Unit>

    suspend fun getAllTobaccosList(token: String): Flow<ApiState<List<RandomRecipeSubListItem>>>

    suspend fun getListRecipes(token: String): Flow<ApiState<List<ModelRecipeItem>>>

    suspend fun getListArchiveRecipes(): List<RandomRecipeSubListItem>

    suspend fun getOrdersList(token: String): Flow<ApiState<List<OrdersItem>>>

    suspend fun reduceRecipe(recipe: List<ReduceRecipeRequest>): ReduceRecipeResponse

    suspend fun createOrder(order: OrderRequest): OrderResponse

    suspend fun updateOrder(id: Int, recipes: OrderRequest): OrderResponse

    suspend fun getInfoOrder(id: Int): Flow<ApiState<OrderResponse>>

    suspend fun deleteOrder(id: Int)

}