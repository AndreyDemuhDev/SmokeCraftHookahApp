package com.pidzama.smokecrafthookahapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.navigation.compose.rememberNavController
import com.pidzama.smokecrafthookahapp.navigation.RootNavGraph
import com.pidzama.smokecrafthookahapp.ui.theme.SmokeCraftHookahAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

//    @Inject
//    lateinit var splashScreenViewModel: SplashScreenViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        installSplashScreen().setKeepOnScreenCondition {
//            !splashScreenViewModel.isLoading.value
//        }


        setContent {
            var darkTheme by remember { mutableStateOf(false) }
            SmokeCraftHookahAppTheme(darkTheme = darkTheme) {
                val navController = rememberNavController()
//                val startScreen by splashScreenViewModel.startDestination
                RootNavGraph(
                    navController = navController,
                    darkTheme = darkTheme,
                    onThemeUpdated = { darkTheme = !darkTheme }
//                    startDestination = startScreen
                )
            }
        }
    }
}
