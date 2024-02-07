package com.pidzama.smokecrafthookahapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pidzama.smokecrafthookahapp.presentation.main.MainScreen

@Composable
fun RootNavGraph(
//    startDestination: String,
    navController: NavHostController,
    darkTheme: Boolean, onThemeUpdated: () -> Unit
) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Graph.MAIN
    ) {
//        onBoardingGraph(navController = navController)
        authNavGraph(navController = navController)
        composable(route = Graph.MAIN) {
            MainScreen(darkTheme = darkTheme, onThemeUpdated = onThemeUpdated)
        }
    }
}

object Graph {
    const val ROOT = "root_graph"

    //    const val ON_BOARDING = "on_boarding_graph"
    const val AUTH = "auth_graph"
    const val MAIN = "main_graph"
}