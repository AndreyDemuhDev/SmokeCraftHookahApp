package com.pidzama.smokecrafthookahapp.presentation.recipe_generation_method

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pidzama.smokecrafthookahapp.data.model.generate_model.ModelRecipeItem
import com.pidzama.smokecrafthookahapp.data.network.doOnFailure
import com.pidzama.smokecrafthookahapp.data.network.doOnLoading
import com.pidzama.smokecrafthookahapp.data.network.doOnSuccess
import com.pidzama.smokecrafthookahapp.data.repository.JwtTokenDataStore
import com.pidzama.smokecrafthookahapp.domain.use_case.AppUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeGenerationViewModel @Inject constructor(
    private val dataStoreToken: JwtTokenDataStore,
    private val useCase: AppUseCase
) : ViewModel() {

    private val _data = MutableStateFlow(0)
    val data: MutableStateFlow<Int>
        get() = _data

    private val _token = MutableStateFlow("")
    val token: StateFlow<String> = _token

    private val _res: MutableState<GenerateRecipeUiState> = mutableStateOf(GenerateRecipeUiState())
    val res: State<GenerateRecipeUiState> = _res

    private val _swipeIsLoading = MutableStateFlow(false)
    val swipeIsLoading = _swipeIsLoading.asStateFlow()

    init {
        getUserToken()
        useSwipe()
        getListRecipes(
            token = token.value
        )
        Log.d("MyLog", "token viewModel ${token.value}")
    }

    fun getUserToken() {
        viewModelScope.launch {
            _token.value = dataStoreToken.getAccessJwt().toString()
            Log.d("MyLog", "GET ACCESS JWT viewModel ${dataStoreToken.getAccessJwt().toString()}")
        }
    }

    fun getListRecipes(token: String) {
        try {
            viewModelScope.launch {
                useCase.recipes.getListRecipes(token)
                    .doOnSuccess {
                        _res.value = GenerateRecipeUiState(
                            data = it
                        )
                        Log.d("MyLog", "data $it")

                    }.doOnFailure {
                        _res.value = GenerateRecipeUiState(
                            error = it.message!!
                        )
                        Log.d("MyLog", "message ${it.message}")
                    }.doOnLoading {
                        _res.value = GenerateRecipeUiState(
                            isLoading = true
                        )
                    }.collect()
            }
        } catch (e: Exception) {
            _res.value = GenerateRecipeUiState(
                error = e.message!!
            )
            Log.d("MyLog", "error $e")
        }
    }


    fun updateRecipesIndex(newData: Int) {
        _data.value = newData + 3
    }

    fun useSwipe() {
        viewModelScope.launch {
            _swipeIsLoading.value = true
            delay(3000L)
            _swipeIsLoading.value = false
        }
    }

}

fun String.substringToken(token: String) =
    this.substring(1, token.length - 1)

data class GenerateRecipeUiState(
    val data: List<ModelRecipeItem> = emptyList(),
    val error: String = "",
    val isLoading: Boolean = false
)
