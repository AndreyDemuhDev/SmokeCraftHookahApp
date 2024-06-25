package com.pidzama.smokecrafthookahapp.data.repository

import com.pidzama.smokecrafthookahapp.data.local.RecipeDao
import com.pidzama.smokecrafthookahapp.data.dto.RandomRecipeSubListItem
import com.pidzama.smokecrafthookahapp.data.dto.generate_model.ModelRecipeItem
import com.pidzama.smokecrafthookahapp.data.dto.orders.OrdersItem
import com.pidzama.smokecrafthookahapp.data.network.ApiState
import com.pidzama.smokecrafthookahapp.data.network.SmokeCraftApi
import com.pidzama.smokecrafthookahapp.data.remote.authorization.AuthRequest
import com.pidzama.smokecrafthookahapp.data.remote.order.OrderRequest
import com.pidzama.smokecrafthookahapp.data.remote.order.OrderResponse
import com.pidzama.smokecrafthookahapp.data.remote.reduce.ReduceRecipeRequest
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
    private val jwtTokenManager: JwtTokenDataStore,
    private val dataStore: DataStoreRepository,
) : RecipeRepository, SafeDataRepository() {


    override suspend fun loginUser(login: AuthRequest): StatusAuth<Unit> {
        return try {
            val response = apiService.getJwtTokenUser(login)
//            apiService.getJwtTokenUser(login)
            jwtTokenManager.saveAccessJwt(response.access)
            jwtTokenManager.saveRefreshJwt(response.refresh)
            dataStore.saveUserLogin(login.username)
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


    override suspend fun getListRecipes(token: String): Flow<ApiState<List<ModelRecipeItem>>> =
        safeApiCall {
            apiService.getRandomGenerateRecipeList(token = token)
        }

    override suspend fun getListArchiveRecipes(): List<RandomRecipeSubListItem> {
        return recipeDao.getListArchiveTobaccos()
    }

    override suspend fun getOrdersList(token: String): Flow<ApiState<List<OrdersItem>>> =
        safeApiCall {
            apiService.getOrdersList(token)
        }

    override suspend fun reduceRecipe(recipe: List<ReduceRecipeRequest>): ReduceRecipeResponse {
        return apiService.reduceRecipe(recipe)
    }

    override suspend fun createOrder(order: OrderRequest): OrderResponse {
        return apiService.createOrder(order = order)
    }

    override suspend fun updateOrder(id: Int, recipes: OrderRequest): OrderResponse {
        return apiService.updateOrder(id = id, recipes = recipes)
    }

    override suspend fun getInfoOrder(id: Int): Flow<ApiState<OrderResponse>> =
        safeApiCall {
            apiService.getInfoOrder(id = id)
        }

    override suspend fun deleteOrder(id: Int) {
        apiService.deleteOrder(id = id)
    }
}
