package com.pidzama.smokecrafthookahapp.presentation.detail_hookah

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pidzama.smokecrafthookahapp.data.model.RandomRecipeSubList
import com.pidzama.smokecrafthookahapp.data.remote.reduce.ReduceRecipeResponse
import com.pidzama.smokecrafthookahapp.data.repository.SmokeCraftRepository
import com.pidzama.smokecrafthookahapp.navigation.Graph
import com.pidzama.smokecrafthookahapp.presentation.auth.AuthState
import com.pidzama.smokecrafthookahapp.presentation.auth.common.UiEvents
import com.pidzama.smokecrafthookahapp.utils.StatusAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailHookahViewModel @Inject constructor(
    private val smokeCraftRepository: SmokeCraftRepository
) : ViewModel() {


    private val _reduceRecipe = MutableLiveData<ReduceRecipeResponse>()
    val reduceRecipe: LiveData<ReduceRecipeResponse>
        get() = _reduceRecipe


    fun reduceRecipe(recipe: RandomRecipeSubList) {
        viewModelScope.launch {
            smokeCraftRepository.reduceRecipe(recipe).let {
                if (it.isSuccessful) {
                    _reduceRecipe.postValue(it.body())
                } else {
                    it.errorBody()
                }
            }
        }
    }
}