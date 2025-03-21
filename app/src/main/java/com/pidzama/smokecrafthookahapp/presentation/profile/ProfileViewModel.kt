package com.pidzama.smokecrafthookahapp.presentation.profile

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pidzama.smokecrafthookahapp.data.repository.DataStoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class ProfileViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    private val _isLoading: MutableState<Boolean> = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _isDarkMode: MutableState<Boolean> = mutableStateOf(false)
    val isDarkMode: State<Boolean> = _isDarkMode

    private val _login: MutableState<String> = mutableStateOf("")
    val login: State<String> = _login

    init {
        viewModelScope.launch {
            dataStoreRepository.getThemeDataStore().collect { isDark ->
                _isDarkMode.value = isDark
            }
            _isLoading.value = false
        }
    }

    fun getUserLogin() {
        viewModelScope.launch {
            dataStoreRepository.getUserLogin().collect { login ->
                _login.value = login.toString()
            }
        }
    }

    fun saveThemeMode(isDarkMode: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveThemeMode(isDarkMode = isDarkMode)
        }
    }
}