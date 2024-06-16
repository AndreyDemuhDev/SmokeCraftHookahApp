package com.pidzama.smokecrafthookahapp.presentation.detail_order

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pidzama.smokecrafthookahapp.data.network.doOnFailure
import com.pidzama.smokecrafthookahapp.data.network.doOnLoading
import com.pidzama.smokecrafthookahapp.data.network.doOnSuccess
import com.pidzama.smokecrafthookahapp.data.remote.order.OrderResponse
import com.pidzama.smokecrafthookahapp.domain.use_case.AppUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class DetailOrderViewModel @Inject constructor(
    private val useCase: AppUseCase
) : ViewModel() {

    private val _detailOrderState = MutableStateFlow<DetailOrderState>(DetailOrderState.Initial)
    val detailOrderState: StateFlow<DetailOrderState>
        get() = _detailOrderState.asStateFlow()


    fun getInfoOrder(id: Int) {
        viewModelScope.launch {
            try {
                useCase.getInfoOrder.getOrderInfo(id = id)
                    .doOnSuccess {
                        Log.d("MyLog", "тут id $id")
                        _detailOrderState.value = DetailOrderState.Content(it)
                    }
                    .doOnFailure {
                        _detailOrderState.value = DetailOrderState.Error("Ошибка")
                    }
                    .doOnLoading {
                        Log.d("MyLog", "тут id $id")
                        _detailOrderState.value = DetailOrderState.Loading
                    }
            } catch (e: HttpException) {
                _detailOrderState.value = DetailOrderState.Error(error = "Ошибка соединения")
            } catch (e: Exception) {
                _detailOrderState.value = DetailOrderState.Error(error = "Error")
            }

        }

    }

}


sealed interface DetailOrderState {

    data class Content(
        val data: OrderResponse
    ) : DetailOrderState

    data class Error(
        val error: String
    ) : DetailOrderState

    object Loading : DetailOrderState

    companion object {
        val Initial = Loading
    }
}