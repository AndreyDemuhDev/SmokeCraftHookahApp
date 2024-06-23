package com.pidzama.smokecrafthookahapp.domain.use_case

import com.pidzama.smokecrafthookahapp.domain.repository.RecipeRepository
import javax.inject.Inject

class DeleteOrderUseCase @Inject constructor(
    private val repo: RecipeRepository
) {

    suspend fun deleteOrder(id: Int) {
        repo.deleteOrder(id = id)
    }
}