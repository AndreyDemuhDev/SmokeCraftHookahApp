package com.pidzama.smokecrafthookahapp.presentation.detail_hookah

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pidzama.smokecrafthookahapp.data.model.RandomRecipeSubList
import com.pidzama.smokecrafthookahapp.data.network.doOnFailure
import com.pidzama.smokecrafthookahapp.data.network.doOnLoading
import com.pidzama.smokecrafthookahapp.data.network.doOnSuccess
import com.pidzama.smokecrafthookahapp.data.remote.reduce.ReduceRecipeRequest
import com.pidzama.smokecrafthookahapp.data.remote.reduce.ReduceRecipeResponse
import com.pidzama.smokecrafthookahapp.data.repository.SmokeCraftRepository
import com.pidzama.smokecrafthookahapp.domain.use_case.ReduceRecipeUseCase
import com.pidzama.smokecrafthookahapp.navigation.Graph
import com.pidzama.smokecrafthookahapp.navigation.MainScreen
import com.pidzama.smokecrafthookahapp.presentation.auth.AuthState
import com.pidzama.smokecrafthookahapp.presentation.auth.common.UiEvents
import com.pidzama.smokecrafthookahapp.presentation.current_orders.MovieState
import com.pidzama.smokecrafthookahapp.utils.StatusAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailHookahViewModel @Inject constructor(
    private val useCase: ReduceRecipeUseCase
) : ViewModel() {


    private val _res: MutableState<RecipeState> = mutableStateOf(RecipeState())
    val res: State<RecipeState> = _res


    fun reduceRecipe(recipe: RandomRecipeSubList) {
        viewModelScope.launch {
            useCase.reduceRecipe(recipe)
                .doOnSuccess {
                    _res.value = RecipeState(
                        data = it
                    )
                }.doOnFailure {
                    _res.value = RecipeState(
                        error = it.message!!
                    )
                }.collect()
        }
    }
}

data class RecipeState(
    val data: ReduceRecipeResponse? = null,
    val error: String = ""
)