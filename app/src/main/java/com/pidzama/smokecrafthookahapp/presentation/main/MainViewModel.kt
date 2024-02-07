package com.pidzama.smokecrafthookahapp.presentation.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pidzama.smokecrafthookahapp.data.DataStoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class MainViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    private val _isLoading: MutableState<Boolean> = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _isDarkMode: MutableState<Boolean> = mutableStateOf(false)
    val isDarkMode: State<Boolean> = _isDarkMode

    fun saveThemeMode(isDarkMode: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveThemeMode(isDarkMode = isDarkMode)
        }
    }

    init {
        viewModelScope.launch {
            dataStoreRepository.getThemeDataStore().collect { isDark ->
                _isDarkMode.value = isDark
            }
            _isLoading.value = false
        }
    }

}