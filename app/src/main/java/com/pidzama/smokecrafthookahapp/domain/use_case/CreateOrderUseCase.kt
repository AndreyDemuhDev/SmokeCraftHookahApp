package com.pidzama.smokecrafthookahapp.domain.use_case

import com.pidzama.smokecrafthookahapp.data.remote.order.OrderRequest
import com.pidzama.smokecrafthookahapp.data.remote.order.OrderResponse
import com.pidzama.smokecrafthookahapp.domain.repository.RecipeRepository
import javax.inject.Inject

class CreateOrderUseCase @Inject constructor(
    private val repo: RecipeRepository
) {

    suspend fun createOrder(order: OrderRequest): OrderResponse {
        return repo.createOrder(order = order)
    }

    suspend fun updateOrder(id: Int, token: String, recipes: OrderRequest): OrderResponse {
        return repo.updateOrder(id = id, token = token, recipes = recipes)
    }
}