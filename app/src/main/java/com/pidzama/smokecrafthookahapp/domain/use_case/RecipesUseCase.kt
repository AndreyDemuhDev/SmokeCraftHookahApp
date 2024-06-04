package com.pidzama.smokecrafthookahapp.domain.use_case

import com.pidzama.smokecrafthookahapp.data.model.generate_model.ModelRecipeItem
import com.pidzama.smokecrafthookahapp.data.network.ApiState
import com.pidzama.smokecrafthookahapp.data.network.map
import com.pidzama.smokecrafthookahapp.domain.model.RecipeMapper
import com.pidzama.smokecrafthookahapp.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RecipesUseCase @Inject constructor(
    private val repo: RecipeRepository,
    private val mapper: RecipeMapper
) {

    suspend fun getListRecipes(token: String): Flow<ApiState<List<ModelRecipeItem>>> {
        return repo.getListRecipes(token).map { result ->
            result.map {
                mapper.fromMap(it)
            }
        }
    }
}
