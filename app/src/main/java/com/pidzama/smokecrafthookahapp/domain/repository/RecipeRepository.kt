package com.pidzama.smokecrafthookahapp.domain.repository

import com.pidzama.smokecrafthookahapp.data.model.RandomRecipeSubList
import com.pidzama.smokecrafthookahapp.data.model.RandomRecipeSubListItem
import com.pidzama.smokecrafthookahapp.data.network.ApiState
import com.pidzama.smokecrafthookahapp.data.remote.authorization.AuthRequest
import com.pidzama.smokecrafthookahapp.data.remote.reduce.ReduceRecipeResponse
import com.pidzama.smokecrafthookahapp.presentation.auth.common.StatusAuth
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {

    suspend fun loginUser(login: AuthRequest): StatusAuth<Unit>

    suspend fun getListRecipes(token: String): Flow<ApiState<List<RandomRecipeSubList>>>

    suspend fun reduceRecipe(recipe: RandomRecipeSubList): Flow<ApiState<ReduceRecipeResponse>>

    suspend fun getListArchiveRecipes(): List<RandomRecipeSubListItem>

    suspend fun insertRecipeToArchive(recipe: List<RandomRecipeSubListItem>)

}