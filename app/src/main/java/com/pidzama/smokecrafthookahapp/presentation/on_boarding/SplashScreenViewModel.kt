package com.pidzama.smokecrafthookahapp.presentation.on_boarding

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pidzama.smokecrafthookahapp.data.DataStoreRepository
import com.pidzama.smokecrafthookahapp.navigation.Graph
import kotlinx.coroutines.launch
import javax.inject.Inject

//class SplashScreenViewModel @Inject constructor(
//    private val dataStoreRepository: DataStoreRepository
//) : ViewModel() {
//
//    private val _isLoading: MutableState<Boolean> = mutableStateOf(true)
//    val isLoading: State<Boolean> = _isLoading
//
//    private val _startDestination: MutableState<String> = mutableStateOf(Graph.ON_BOARDING)
//    val startDestination: State<String> = _startDestination
//
//    init {
//        viewModelScope.launch {
//            dataStoreRepository.readOnBoardingState().collect { completed ->
//                if (completed) {
//                    _startDestination.value = Graph.AUTH
//                } else {
//                    _startDestination.value = Graph.ON_BOARDING
//                }
//            }
//            _isLoading.value = false
//        }
//    }
//}