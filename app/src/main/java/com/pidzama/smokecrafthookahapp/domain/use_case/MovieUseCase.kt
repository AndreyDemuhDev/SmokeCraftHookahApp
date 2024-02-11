package com.pidzama.smokecrafthookahapp.domain.use_case

import com.pidzama.smokecrafthookahapp.data.model.RandomRecipeSubList
import com.pidzama.smokecrafthookahapp.data.network.ApiState
import com.pidzama.smokecrafthookahapp.data.network.map
import com.pidzama.smokecrafthookahapp.data.repository.MovieRepository
import com.pidzama.smokecrafthookahapp.domain.mapper.MovieMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieUseCase @Inject constructor(
    private val repo: MovieRepository,
    private val mapper: MovieMapper
) {

    suspend fun getMovies(token: String): Flow<ApiState<List<RandomRecipeSubList>>> {
        return repo.getMovie(token).map { result ->
            result.map {
                mapper.fromMap(it)
            }
        }
    }
}
