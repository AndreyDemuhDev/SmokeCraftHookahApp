package com.pidzama.smokecrafthookahapp.presentation.current_orders

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pidzama.smokecrafthookahapp.data.model.RandomRecipeSubList
import com.pidzama.smokecrafthookahapp.data.network.doOnFailure
import com.pidzama.smokecrafthookahapp.data.network.doOnLoading
import com.pidzama.smokecrafthookahapp.data.network.doOnSuccess
import com.pidzama.smokecrafthookahapp.data.repository.DataStoreRepository
import com.pidzama.smokecrafthookahapp.domain.use_case.AppUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrentOrdersViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    private val useCase: AppUseCase
) : ViewModel() {

    private val _data = MutableStateFlow(0)
    val data: MutableStateFlow<Int>
        get() = _data

    private val _token: MutableState<String> = mutableStateOf("")
    val token: State<String> = _token

    private val _res: MutableState<MovieState> = mutableStateOf(MovieState())
    val res: State<MovieState> = _res

    private val _swipeIsLoading = MutableStateFlow(false)
    val swipeIsLoading = _swipeIsLoading.asStateFlow()

    init {
        getUserToken()
        useSwipe()
        getListRecipes(
            token = "Token ${
                token.value.substringToken(token.value)
            }"
        )
    }

    fun getUserToken() {
        viewModelScope.launch {
            dataStoreRepository.getAuthToken().collect { getToken ->
                _token.value = getToken.toString()
            }
        }
    }

    fun getListRecipes(token: String) {
        try{
            viewModelScope.launch {
                useCase.recipes.getMovies(token)
                    .doOnSuccess {
                        _res.value = MovieState(
                            data = it
                        )
                    }.doOnFailure {
                        _res.value = MovieState(
                            error = it.message!!
                        )
                    }.doOnLoading {
                        _res.value = MovieState(
                            isLoading = true
                        )
                    }.collect()
            }
        } catch (e: Exception){
            _res.value = MovieState(
                error = e.message!!
            )
            Log.d("MyLog", "error $e")
        }

    }

    fun updateRecipesIndex(newData: Int) {
        _data.value = newData + 3
    }

    fun useSwipe(){
        viewModelScope.launch {
            _swipeIsLoading.value = true
            delay(3000L)
            _swipeIsLoading.value = false
        }
    }

}

fun String.substringToken(token: String) =
    this.substring(1, token.length - 1)

data class MovieState(
    val data: List<RandomRecipeSubList> = emptyList(),
    val error: String = "",
    val isLoading: Boolean = false
)