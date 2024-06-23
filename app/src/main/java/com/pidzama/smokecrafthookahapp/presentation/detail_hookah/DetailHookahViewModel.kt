package com.pidzama.smokecrafthookahapp.presentation.detail_hookah

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pidzama.smokecrafthookahapp.data.dto.RandomRecipeSubListItem
import com.pidzama.smokecrafthookahapp.data.remote.order.OrderRequest
import com.pidzama.smokecrafthookahapp.data.remote.reduce.ReduceRecipeRequest
import com.pidzama.smokecrafthookahapp.domain.use_case.AppUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailHookahViewModel @Inject constructor(
    private val useCase: AppUseCase,
) : ViewModel() {

    private val _listArchiveTobaccos = MutableStateFlow(emptyList<RandomRecipeSubListItem>())
    val listArchiveTobaccos: StateFlow<List<RandomRecipeSubListItem>> = _listArchiveTobaccos


    fun reduceRecipe(recipe: List<ReduceRecipeRequest>) {
        viewModelScope.launch {
            useCase.reduceRecipeUseCase.reduceRecipe(recipe = recipe)
        }
    }

    fun createOrder(order: OrderRequest) {
        viewModelScope.launch {
            useCase.createOrder.createOrder(order = order)
        }
    }

}
