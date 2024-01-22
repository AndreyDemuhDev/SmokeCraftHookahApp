package com.pidzama.smokecrafthookahapp.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.pidzama.smokecrafthookahapp.presentation.on_boarding.OnBoardingScreen

fun NavGraphBuilder.onBoardingGraph(navController: NavHostController) {
    navigation(
        route = Graph.ON_BOARDING,
        startDestination = OnBoarding.OnBoardings.route
    ) {
        composable(OnBoarding.OnBoardings.route) {
            OnBoardingScreen(navController = navController)
        }
    }
}

sealed class OnBoarding(val route: String) {
    object OnBoardings : OnBoarding("ON_BOARDING")
}