package com.pidzama.smokecrafthookahapp.domain.use_case

import com.pidzama.smokecrafthookahapp.data.model.orders.OrdersItem
import com.pidzama.smokecrafthookahapp.data.network.ApiState
import com.pidzama.smokecrafthookahapp.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OrdersUseCase @Inject constructor(
    private val repo: RecipeRepository,
) {

    suspend fun getOrdersScreen(token: String): Flow<ApiState<List<OrdersItem>>> {
        return repo.getOrdersList(token)
    }
}