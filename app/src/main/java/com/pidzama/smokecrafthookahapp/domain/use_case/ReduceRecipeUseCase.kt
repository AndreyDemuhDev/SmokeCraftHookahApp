package com.pidzama.smokecrafthookahapp.domain.use_case

import com.pidzama.smokecrafthookahapp.data.model.RandomRecipeSubList
import com.pidzama.smokecrafthookahapp.data.network.ApiState
import com.pidzama.smokecrafthookahapp.data.remote.reduce.ReduceRecipeResponse
import com.pidzama.smokecrafthookahapp.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReduceRecipeUseCase @Inject constructor(
    private val repo: RecipeRepository
) {

    suspend fun reduceRecipe(recipe: RandomRecipeSubList): Flow<ApiState<ReduceRecipeResponse>> {
        return repo.reduceRecipe(recipe)
    }
}
