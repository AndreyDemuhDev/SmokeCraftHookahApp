package com.pidzama.smokecrafthookahapp.presentation.profile

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun ProfileScreen(navController: NavHostController) {
    Text(text = "Profile Screen", color = MaterialTheme.colors.primary)
}
