package com.pidzama.smokecrafthookahapp.presentation.auth

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pidzama.smokecrafthookahapp.domain.use_case.LoginUseCase
import com.pidzama.smokecrafthookahapp.navigation.Graph
import com.pidzama.smokecrafthookahapp.presentation.auth.common.TextFieldState
import com.pidzama.smokecrafthookahapp.presentation.auth.common.UiEvents
import com.pidzama.smokecrafthookahapp.utils.StatusAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private var _authState  = mutableStateOf(AuthState())
    val authState: State<AuthState> = _authState

    private val  _eventFlow = MutableSharedFlow<UiEvents>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _loginState = mutableStateOf(TextFieldState())
    val loginState: State<TextFieldState> = _loginState

    fun setLogin(value:String){
        _loginState.value = loginState.value.copy(text = value)
    }

    private val _passwordState = mutableStateOf(TextFieldState())
    val passwordState: State<TextFieldState> = _passwordState

    fun setPassword(value:String){
        _passwordState.value = passwordState.value.copy(text = value)
    }


    fun loginUser(){
        viewModelScope.launch {
            _authState.value = authState.value.copy(isLoading = false)

            val loginResult = loginUseCase(
                login = loginState.value.text,
                password = passwordState.value.text
            )
            _authState.value = authState.value.copy(isLoading = false)

            if (loginResult.emailError != null){
                _loginState.value=loginState.value.copy(error = loginResult.emailError)
            }
            if (loginResult.passwordError != null){
                _passwordState.value = passwordState.value.copy(error = loginResult.passwordError)
            }

            when(loginResult.result){
                is StatusAuth.Success->{
                    _eventFlow.emit(
                        UiEvents.NavigateEvent(Graph.MAIN)
                    )
                }
                is StatusAuth.Error->{
                    _eventFlow.emit(
                        UiEvents.SnackBarEvent(
                            "Невозможно войти в систему с указанными учетными данными."
                        )
                    )
                }
                else -> {

                }
            }
        }
    }
}