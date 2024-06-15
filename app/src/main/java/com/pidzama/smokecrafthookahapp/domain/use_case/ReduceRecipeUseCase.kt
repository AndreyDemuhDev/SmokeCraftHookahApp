package com.pidzama.smokecrafthookahapp.domain.use_case

import com.pidzama.smokecrafthookahapp.data.remote.reduce.ReduceRecipeRequest
import com.pidzama.smokecrafthookahapp.data.remote.reduce.ReduceRecipeResponse
import com.pidzama.smokecrafthookahapp.domain.repository.RecipeRepository
import javax.inject.Inject

class ReduceRecipeUseCase @Inject constructor(
    private val repo: RecipeRepository
) {

    suspend fun reduceRecipe(recipe: List<ReduceRecipeRequest>): ReduceRecipeResponse {
        return repo.reduceRecipe(recipe = recipe)
    }
}
