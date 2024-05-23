package com.pidzama.smokecrafthookahapp.presentation.archive_orders

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pidzama.smokecrafthookahapp.data.model.RandomRecipeSubListItem
import com.pidzama.smokecrafthookahapp.data.network.doOnFailure
import com.pidzama.smokecrafthookahapp.data.network.doOnSuccess
import com.pidzama.smokecrafthookahapp.data.repository.DataStoreRepository
import com.pidzama.smokecrafthookahapp.domain.use_case.AppUseCase
import com.pidzama.smokecrafthookahapp.presentation.recipe_generation_method.substringToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArchiveViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    private val useCase: AppUseCase
) : ViewModel() {

    private val _token: MutableState<String> = mutableStateOf("")
    val token: State<String> = _token

    private val _listAllTobaccos: MutableState<ListTobaccosState> =
        mutableStateOf(ListTobaccosState())
    val listAllTobaccos: State<ListTobaccosState> = _listAllTobaccos

    init {
//        getUserToken()
//        getAllTobaccosList(
//            token = "Token ${
//                token.value.substringToken(token.value)
//            }"
//        )
    }


    fun getAllTobaccosList(token: String) {
        viewModelScope.launch {
            useCase.getAllTobaccosList.getAllTobaccosList(token)
                .doOnSuccess {
                    _listAllTobaccos.value = ListTobaccosState(
                        data = it
                    )
                }.doOnFailure {
                    _listAllTobaccos.value = ListTobaccosState(
                        error = it.message!!
                    )
                }.collect()
        }
    }

//    fun getUserToken() {
//        viewModelScope.launch {
//            dataStoreRepository.getAuthToken().collect { getToken ->
//                _token.value = getToken.toString()
//            }
//        }
//    }

}


data class ListTobaccosState(
    val data: List<RandomRecipeSubListItem> = emptyList(),
    val error: String = ""
)