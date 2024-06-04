package com.pidzama.smokecrafthookahapp.domain.use_case

import com.pidzama.smokecrafthookahapp.domain.repository.RecipeRepository
import javax.inject.Inject

class ReduceRecipeUseCase @Inject constructor(
    private val repo: RecipeRepository
) {

//    suspend fun reduceRecipe(recipe: NewRecipeItem): Flow<ApiState<ReduceRecipeResponse>> {
//        return repo.reduceRecipe(recipe)
//    }
}
