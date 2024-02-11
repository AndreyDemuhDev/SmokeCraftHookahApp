package com.pidzama.smokecrafthookahapp.domain.use_case

import com.pidzama.smokecrafthookahapp.data.model.RandomRecipeSubList
import com.pidzama.smokecrafthookahapp.data.network.ApiState
import com.pidzama.smokecrafthookahapp.data.network.map
import com.pidzama.smokecrafthookahapp.data.repository.RecipeRepository
import com.pidzama.smokecrafthookahapp.domain.mapper.RecipeMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RecipesUseCase @Inject constructor(
    private val repo: RecipeRepository,
    private val mapper: RecipeMapper
) {

    suspend fun getMovies(token: String): Flow<ApiState<List<RandomRecipeSubList>>> {
        return repo.getListRecipes(token).map { result ->
            result.map {
                mapper.fromMap(it)
            }
        }
    }

}
