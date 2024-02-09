package com.pidzama.smokecrafthookahapp.presentation.current_orders

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pidzama.smokecrafthookahapp.data.DataStoreRepository
import com.pidzama.smokecrafthookahapp.data.model.NewRandomRecipeSubList
import com.pidzama.smokecrafthookahapp.data.model.RandomRecipe
import com.pidzama.smokecrafthookahapp.data.model.RandomRecipeSubList
import com.pidzama.smokecrafthookahapp.data.repository.SmokeCraftRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrentOrdersViewModel @Inject constructor(
    private val smokeCraftRepository: SmokeCraftRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    private val _data = MutableStateFlow(0)
    val data: MutableStateFlow<Int>
        get() = _data

    private val _generateRecipeList = MutableLiveData<List<RandomRecipeSubList>>()
    val generateRecipeList: LiveData<List<RandomRecipeSubList>>
        get() = _generateRecipeList

    private val _token: MutableState<String> = mutableStateOf("")
    val token: State<String> = _token

    fun getUserToken(){
        viewModelScope.launch {
            dataStoreRepository.getAuthToken().collect{ getToken->
                _token.value = getToken.toString()
            }
        }
    }

    fun getListRandomGenerateRecipe(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            smokeCraftRepository.getRandomRecipe(token).let {
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

}

fun String.addEnthusiasm(token: String) =
    this.substring(1, token.length - 1)