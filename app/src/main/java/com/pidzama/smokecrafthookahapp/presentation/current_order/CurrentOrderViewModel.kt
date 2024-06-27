package com.pidzama.smokecrafthookahapp.presentation.current_order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pidzama.smokecrafthookahapp.data.dto.orders.OrdersItem
import com.pidzama.smokecrafthookahapp.data.network.doOnFailure
import com.pidzama.smokecrafthookahapp.data.network.doOnLoading
import com.pidzama.smokecrafthookahapp.data.network.doOnSuccess
import com.pidzama.smokecrafthookahapp.data.repository.JwtTokenDataStore
import com.pidzama.smokecrafthookahapp.domain.use_case.AppUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class CurrentOrderViewModel @Inject constructor(
    private val useCase: AppUseCase,
    private val dataStoreToken: JwtTokenDataStore,
) : ViewModel() {


    private val _stateOrder = MutableStateFlow<OrdersState>(OrdersState.Initial)
    val stateOrdersState: StateFlow<OrdersState>
        get() = _stateOrder.asStateFlow()

    init {
        getOrderList()
    }


    private fun getOrderList() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                useCase.ordersList.getOrdersList(dataStoreToken.getAccessJwt().toString())
                    .doOnSuccess {
                        if (it.isNotEmpty()) {
                            _stateOrder.value = OrdersState.Content(it)
                        } else {
                            _stateOrder.value = OrdersState.Empty(message = "Список заказов пуст")
                        }
                    }
                    .doOnFailure {
                        _stateOrder.value = OrdersState.Error("Ошибка загрузки")
                    }
                    .doOnLoading {
                        _stateOrder.value = OrdersState.Loading
                    }.collect()
            } catch (e: HttpException) {
                _stateOrder.value = OrdersState.Error(error = "Ошибка соединения")
            } catch (e: Exception) {
                _stateOrder.value = OrdersState.Error(error = "Error")
            }
        }
    }
}


sealed interface OrdersState {

    data class Content(
        val data: List<OrdersItem>
    ) : OrdersState

    data class Error(
        val error: String
    ) : OrdersState

    data class Empty(
        val message: String
    ) : OrdersState

    object Loading : OrdersState

    companion object {
        val Initial: OrdersState = Loading
    }
}