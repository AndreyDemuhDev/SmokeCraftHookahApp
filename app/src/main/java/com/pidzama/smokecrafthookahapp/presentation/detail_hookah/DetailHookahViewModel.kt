package com.pidzama.smokecrafthookahapp.presentation.detail_hookah

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.yml.charts.common.extensions.isNotNull
import com.pidzama.smokecrafthookahapp.data.dto.RandomRecipeSubListItem
import com.pidzama.smokecrafthookahapp.data.dto.generate_model.ModelRecipeItem
import com.pidzama.smokecrafthookahapp.data.network.doOnFailure
import com.pidzama.smokecrafthookahapp.data.network.doOnLoading
import com.pidzama.smokecrafthookahapp.data.network.doOnSuccess
import com.pidzama.smokecrafthookahapp.data.remote.order.OrderRequest
import com.pidzama.smokecrafthookahapp.data.remote.order.OrderResponse
import com.pidzama.smokecrafthookahapp.data.remote.reduce.ReduceRecipeRequest
import com.pidzama.smokecrafthookahapp.domain.entities.RecipeModelEntity
import com.pidzama.smokecrafthookahapp.domain.use_case.AppUseCase
import com.pidzama.smokecrafthookahapp.presentation.detail_order.DetailOrderState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class DetailHookahViewModel @Inject constructor(
    private val useCase: AppUseCase,
) : ViewModel() {

//    private val _listArchiveTobaccos = MutableStateFlow(emptyList<RandomRecipeSubListItem>())
//    val listArchiveTobaccos: StateFlow<List<RandomRecipeSubListItem>> = _listArchiveTobaccos

    private val _detailHookahState = MutableStateFlow(DetailHookahState.Initial)
    val detailHookahState: StateFlow<DetailHookahState>
        get() = _detailHookahState.asStateFlow()


    fun getInfoHookah(recipe: RecipeModelEntity, idOrder: Int) {
        viewModelScope.launch {
            try {
                if (idOrder <= 0) {
                    _detailHookahState.value = DetailHookahState.ContentOnlyRecipe(recipe)
                } else {
                    useCase.getInfoOrder.getOrderInfo(id = idOrder)
                        .doOnSuccess { order ->
                            _detailHookahState.value =
                                DetailHookahState.ContentRecipeWithOrder(recipe, order)
                        }
                        .doOnFailure {
                            _detailHookahState.value = DetailHookahState.Error("Ошибка")
                        }.doOnLoading {
                            _detailHookahState.value = DetailHookahState.Loading
                        }.collect()
                }
            } catch (e: HttpException) {
                _detailHookahState.value = DetailHookahState.Error(error = "Ошибка соединения")
            } catch (e: Exception) {
                _detailHookahState.value = DetailHookahState.Error(error = "Error")
            }
        }
    }


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

    fun updateOrder(id: Int, recipes: OrderRequest) {
        viewModelScope.launch {
            useCase.createOrder.updateOrder(id = id, recipes = recipes)
        }
    }

}

sealed interface DetailHookahState {

    data class ContentOnlyRecipe(
        val data: RecipeModelEntity,
    ) : DetailHookahState

    data class ContentRecipeWithOrder(
        val data: RecipeModelEntity,
        val orderData: OrderResponse
    ) : DetailHookahState

    data class Error(
        val error: String
    ) : DetailHookahState

    object Loading : DetailHookahState

    companion object {
        val Initial: DetailHookahState = Loading
    }
}
