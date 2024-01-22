package com.pidzama.smokecrafthookahapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.pidzama.smokecrafthookahapp.navigation.RootNavGraph
import com.pidzama.smokecrafthookahapp.presentation.on_boarding.SplashScreenViewModel
import com.pidzama.smokecrafthookahapp.ui.theme.SmokeCraftHookahAppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var splashScreenViewModel: SplashScreenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().setKeepOnScreenCondition {
            !splashScreenViewModel.isLoading.value
        }


        setContent {
            SmokeCraftHookahAppTheme {
                val navController = rememberNavController()
                val startScreen by splashScreenViewModel.startDestination
                RootNavGraph(
                    navController = navController,
                    startDestination = startScreen
                )
            }
        }
    }
}
