package com.pidzama.smokecrafthookahapp.data.repository

import com.pidzama.smokecrafthookahapp.data.model.RandomRecipeSubList
import com.pidzama.smokecrafthookahapp.data.network.ApiState
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {

    suspend fun getListRecipes(token: String): Flow<ApiState<List<RandomRecipeSubList>>>
}