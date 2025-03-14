package com.pidzama.smokecrafthookahapp.domain.use_case

import com.pidzama.smokecrafthookahapp.data.network.ApiState
import com.pidzama.smokecrafthookahapp.data.remote.order.OrderRequest
import com.pidzama.smokecrafthookahapp.data.remote.order.OrderResponse
import com.pidzama.smokecrafthookahapp.domain.entities.RecipeModelEntity
import com.pidzama.smokecrafthookahapp.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreateOrderUseCase @Inject constructor(
    private val repo: RecipeRepository
) {

    suspend fun createOrder(order: OrderRequest): OrderResponse {
        return repo.createOrder(order = order)
    }

    suspend fun updateOrder(id: Int, order: OrderRequest): OrderResponse {
        return repo.updateOrder(id = id, order = order)
    }
}