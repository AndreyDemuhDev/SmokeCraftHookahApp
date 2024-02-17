package com.pidzama.smokecrafthookahapp.domain.use_case

import com.pidzama.smokecrafthookahapp.data.model.RandomRecipeSubListItem
import com.pidzama.smokecrafthookahapp.data.network.ApiState
import com.pidzama.smokecrafthookahapp.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllTobaccosListUseCase @Inject constructor(
    private val repo: RecipeRepository
) {

    suspend fun getAllTobaccosList(token: String): Flow<ApiState<List<RandomRecipeSubListItem>>> {
        return repo.getAllTobaccosList(token)
    }
}