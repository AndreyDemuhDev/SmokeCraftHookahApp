package com.pidzama.smokecrafthookahapp.presentation.profile

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.pidzama.smokecrafthookahapp.presentation.common.ThemeSwitcher
import com.pidzama.smokecrafthookahapp.presentation.main.MainViewModel

@Composable
fun ProfileScreen(
    onBoardingViewModel: MainViewModel,
    darkTheme: Boolean, onThemeUpdated: () -> Unit
) {
    Text(text = "Profile Screen", color = MaterialTheme.colors.primary)
    var isDark by remember {
        mutableStateOf(onBoardingViewModel.isDarkMode.value)
    }

    ThemeSwitcher(
        darkTheme = darkTheme,
        onClick = {
            isDark = !isDark
            onBoardingViewModel.saveThemeMode(isDarkMode = isDark)
            onThemeUpdated()
        }
    )
}
