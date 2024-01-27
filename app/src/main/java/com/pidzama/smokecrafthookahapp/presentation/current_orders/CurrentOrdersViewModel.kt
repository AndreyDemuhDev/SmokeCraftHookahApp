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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrentOrdersViewModel @Inject constructor(
    private val smokeCraftRepository: SmokeCraftRepository
) : ViewModel() {

    private val _allWorkers = MutableLiveData<List<WorkerItem>>()
    val allWorkers: LiveData<List<WorkerItem>>
        get() = _allWorkers

    private val _login = MutableLiveData<WorkerItem>()
    val login: LiveData<WorkerItem>
        get() = _login

    private val _generateRecipeList = MutableLiveData<List<RandomRecipeSubList>>()
    val generateRecipeList: LiveData<List<RandomRecipeSubList>>
        get() = _generateRecipeList


    fun getAllWorkers() {
        viewModelScope.launch {
            smokeCraftRepository.getAllWorkers().let {
                if (it.isSuccessful) {
                    _allWorkers.postValue(it.body())
                } else {
                    it.errorBody()
                }
            }
        }
    }

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


    val isSuccessLoading = mutableStateOf(value = false)
    private val loginRequestLiveData = MutableLiveData<Boolean>()

//    fun login(email: String, password: String) {
//        viewModelScope.launch(Dispatchers.IO) {
//            try {
//                val authService = smokeCraftRepository.loginUser(LoginRequest(login = email, password = password))
//                Log.d("MyLog", "$authService")
//                if (authService.isSuccessful) {
//                    delay(1500L)
//                    isSuccessLoading.value = true
//                    authService.body()?.let { tokenDto ->
//                        Log.d("Logging", "Response TokenDto: $tokenDto")
//                    }
//                } else {
//                    authService.errorBody()?.let { error ->
//                        delay(1500L)
//                        error.close()
//                    }
//                }
//                loginRequestLiveData.postValue(authService.isSuccessful)
//            } catch (e: Exception) {
//                Log.d("Logging", "Error Authentication", e)
//            }
//        }
//    }
}