package com.pidzama.smokecrafthookahapp.data.repository

import com.pidzama.smokecrafthookahapp.data.local.RecipeDao
import com.pidzama.smokecrafthookahapp.data.model.RandomRecipeSubList
import com.pidzama.smokecrafthookahapp.data.model.RandomRecipeSubListItem
import com.pidzama.smokecrafthookahapp.data.network.ApiState
import com.pidzama.smokecrafthookahapp.data.network.SmokeCraftApi
import com.pidzama.smokecrafthookahapp.data.remote.authorization.AuthRequest
import com.pidzama.smokecrafthookahapp.data.remote.reduce.ReduceRecipeResponse
import com.pidzama.smokecrafthookahapp.domain.repository.RecipeRepository
import com.pidzama.smokecrafthookahapp.presentation.auth.common.StatusAuth
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val apiService: SmokeCraftApi,
    private val recipeDao: RecipeDao,
    private val preferences: DataStoreRepository
) : RecipeRepository, SafeDataRepository() {

    override suspend fun loginUser(login: AuthRequest): StatusAuth<Unit> {
        return try {
            val response = apiService.loginUser(login)
            preferences.saveAuthToken(response.auth_token)
            preferences.saveUserLogin(login.username)
            StatusAuth.Success(Unit)
        } catch (e: IOException) {
            StatusAuth.Error("${e.message}")
        } catch (e: HttpException) {
            StatusAuth.Error("${e.message}")
        }
    }

    override suspend fun getAllTobaccosList(token: String): Flow<ApiState<List<RandomRecipeSubListItem>>> =
        safeApiCall {
            apiService.getAllTobaccosList(token = token)
        }

    override suspend fun getListRecipes(token: String): Flow<ApiState<List<RandomRecipeSubList>>> =
        safeApiCall {
            apiService.getRandomGenerateRecipeList(token = token)
        }

    override suspend fun reduceRecipe(recipe: RandomRecipeSubList): Flow<ApiState<ReduceRecipeResponse>> =
        safeApiCall {
            apiService.reduceRecipe(recipe = recipe)
        }

    override suspend fun getListArchiveRecipes(): List<RandomRecipeSubListItem> {
        return recipeDao.getListArchiveTobaccos()
    }

    override suspend fun insertRecipeToArchive(recipe: List<RandomRecipeSubListItem>) {
        return recipeDao.insertRecipeToDataBase(recipe)
    }

}
