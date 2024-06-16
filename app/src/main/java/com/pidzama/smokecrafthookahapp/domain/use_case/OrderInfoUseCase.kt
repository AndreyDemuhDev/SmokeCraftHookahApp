package com.pidzama.smokecrafthookahapp.domain.use_case

import com.pidzama.smokecrafthookahapp.data.network.ApiState
import com.pidzama.smokecrafthookahapp.data.remote.order.OrderResponse
import com.pidzama.smokecrafthookahapp.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OrderInfoUseCase @Inject constructor(
    private val repo: RecipeRepository
) {

    suspend fun getOrderInfo(id: Int): Flow<ApiState<OrderResponse>> {
        return repo.getInfoOrder(id = id)
    }
}