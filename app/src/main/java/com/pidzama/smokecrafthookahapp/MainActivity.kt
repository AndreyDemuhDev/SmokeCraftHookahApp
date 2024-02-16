package com.pidzama.smokecrafthookahapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.pidzama.smokecrafthookahapp.navigation.RootNavGraph
import com.pidzama.smokecrafthookahapp.presentation.profile.ProfileViewModel
import com.pidzama.smokecrafthookahapp.ui.theme.SmokeCraftHookahAppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var splashScreenViewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().setKeepOnScreenCondition() {
            splashScreenViewModel.isLoading.value
        }
        setContent {
            var darkTheme by remember { mutableStateOf(false) }
            SmokeCraftHookahAppTheme(darkTheme = splashScreenViewModel.isDarkMode.value) {
                val navController = rememberNavController()
                RootNavGraph(
                    viewModelMain = splashScreenViewModel,
                    navController = navController,
                    darkTheme = darkTheme,
                    onThemeUpdated = { darkTheme = !darkTheme }
                )
            }
        }
    }
}
