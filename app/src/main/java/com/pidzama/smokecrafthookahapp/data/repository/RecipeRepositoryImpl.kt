package com.pidzama.smokecrafthookahapp.data.repository

import com.pidzama.smokecrafthookahapp.data.model.RandomRecipeSubList
import com.pidzama.smokecrafthookahapp.data.network.ApiState
import com.pidzama.smokecrafthookahapp.data.network.SmokeCraftApi
import com.pidzama.smokecrafthookahapp.data.remote.AuthRequest
import com.pidzama.smokecrafthookahapp.data.remote.reduce.ReduceRecipeRequest
import com.pidzama.smokecrafthookahapp.data.remote.reduce.ReduceRecipeResponse
import com.pidzama.smokecrafthookahapp.utils.StatusAuth
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val apiService: SmokeCraftApi
) : RecipeRepository, BaseRepository() {

    override suspend fun getListRecipes(token: String): Flow<ApiState<List<RandomRecipeSubList>>> =
        safeApiCall {
            apiService.getRandomGenerateRecipeList(token = token)
        }

    override suspend fun reduceRecipe(recipe: RandomRecipeSubList): Flow<ApiState<ReduceRecipeResponse>> =
        safeApiCall {
            apiService.reduceRecipe(recipe = recipe)
        }
}
