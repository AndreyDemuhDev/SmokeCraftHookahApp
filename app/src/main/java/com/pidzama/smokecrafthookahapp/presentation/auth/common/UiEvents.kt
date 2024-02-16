package com.pidzama.smokecrafthookahapp.presentation.auth.common

//событие при авторизации(при ошибке->сообщение через снакбар,
// при успешной авторизации переход на другой экран
sealed class UiEvents {
    data class SnackBarEvent(val message : String) : UiEvents()
    data class NavigateEvent(val route: String) : UiEvents()
}