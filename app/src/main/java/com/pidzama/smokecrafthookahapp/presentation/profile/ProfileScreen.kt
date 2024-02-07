package com.pidzama.smokecrafthookahapp.presentation.profile

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.pidzama.smokecrafthookahapp.presentation.common.ThemeSwitcher

@Composable
fun ProfileScreen(navController: NavHostController,
                  darkTheme: Boolean, onThemeUpdated: () -> Unit) {
    Text(text = "Profile Screen", color = MaterialTheme.colors.primary)

    ThemeSwitcher(
        darkTheme = darkTheme,
        onClick = onThemeUpdated
    )

}
