package com.pidzama.smokecrafthookahapp.data.repository

import com.pidzama.smokecrafthookahapp.data.model.RandomRecipeSubList
import com.pidzama.smokecrafthookahapp.data.network.ApiState
import com.pidzama.smokecrafthookahapp.data.network.SmokeCraftApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val apiService: SmokeCraftApi
) : RecipeRepository, BaseRepository() {

    override suspend fun getListRecipes(token: String): Flow<ApiState<List<RandomRecipeSubList>>> =
        safeApiCall {
            apiService.getRandomGenerateRecipeList(token = token)
        }


}
