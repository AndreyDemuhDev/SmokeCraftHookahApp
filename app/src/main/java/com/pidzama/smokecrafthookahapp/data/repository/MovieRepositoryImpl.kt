package com.pidzama.smokecrafthookahapp.data.repository

import com.pidzama.smokecrafthookahapp.data.model.RandomRecipeSubList
import com.pidzama.smokecrafthookahapp.data.network.ApiState
import com.pidzama.smokecrafthookahapp.data.network.SmokeCraftApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val apiService: SmokeCraftApi
) : MovieRepository, BaseRepository() {

    override suspend fun getMovie(token: String): Flow<ApiState<List<RandomRecipeSubList>>> =
        safeAPiCall {
            apiService.getRandomGenerateRecipeList(token = token)
        }
}
