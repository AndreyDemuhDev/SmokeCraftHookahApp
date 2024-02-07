package com.pidzama.smokecrafthookahapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.navigation.compose.rememberNavController
import com.pidzama.smokecrafthookahapp.navigation.RootNavGraph
import com.pidzama.smokecrafthookahapp.presentation.main.MainViewModel
import com.pidzama.smokecrafthookahapp.ui.theme.SmokeCraftHookahAppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var splashScreenViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var darkTheme by remember { mutableStateOf(false) }
            SmokeCraftHookahAppTheme(darkTheme = splashScreenViewModel.isDarkMode.value) {
                val navController = rememberNavController()
                RootNavGraph(
                    viewModelMain =splashScreenViewModel,
                    navController = navController,
                    darkTheme = darkTheme,
                    onThemeUpdated = { darkTheme = !darkTheme }
                )
            }
        }
        Log.d("MyLog", "THEME ===> ${splashScreenViewModel.isDarkMode.value}")
    }
}
