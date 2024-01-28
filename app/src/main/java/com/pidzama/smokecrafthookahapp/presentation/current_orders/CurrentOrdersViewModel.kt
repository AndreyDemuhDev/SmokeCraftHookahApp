package com.pidzama.smokecrafthookahapp.presentation.current_orders

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pidzama.smokecrafthookahapp.data.model.RandomRecipeSubList
import com.pidzama.smokecrafthookahapp.data.model.WorkerItem
import com.pidzama.smokecrafthookahapp.data.repository.SmokeCraftRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrentOrdersViewModel @Inject constructor(
    private val smokeCraftRepository: SmokeCraftRepository
) : ViewModel() {

    private val _data = MutableStateFlow(0)
    val data: MutableStateFlow<Int>
        get() = _data

    private val _login = MutableLiveData<WorkerItem>()
    val login: LiveData<WorkerItem>
        get() = _login

    private val _generateRecipeList = MutableLiveData<List<RandomRecipeSubList>>()
    val generateRecipeList: LiveData<List<RandomRecipeSubList>>
        get() = _generateRecipeList


    fun getListRandomGenerateRecipe() {
        viewModelScope.launch {
            smokeCraftRepository.getRandomRecipe().let {
                if (it.isSuccessful) {
                    _generateRecipeList.postValue(it.body())
                } else {
                    it.errorBody()
                }
            }
        }
    }

    fun updateRecipesIndex(newData: Int) {
        _data.value = newData + 3
    }

    val isSuccessLoading = mutableStateOf(value = false)
    private val loginRequestLiveData = MutableLiveData<Boolean>()

}