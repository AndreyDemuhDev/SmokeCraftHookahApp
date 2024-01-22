package com.pidzama.smokecrafthookahapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pidzama.smokecrafthookahapp.presentation.choose_generate_recipe.CreateRecipeScreen
import com.pidzama.smokecrafthookahapp.presentation.detail_hookah.DetailHookahScreen
import com.pidzama.smokecrafthookahapp.presentation.home.HomeScreen
import com.pidzama.smokecrafthookahapp.presentation.random_generation_recipe.RandomGenerationRecipeScreen
import com.pidzama.smokecrafthookahapp.presentation.settings.SettingsScreen


@Composable
fun MainNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.MAIN,
        startDestination = MainScreen.Home.route
    ) {
        composable(MainScreen.Home.route) {
            CreateRecipeScreen(navController = navController)
        }
        composable(MainScreen.Favorite.route) {
            HomeScreen(navController = navController)
        }
        composable(MainScreen.Settings.route) {
            SettingsScreen(navController = navController)
        }
        composable(MainScreen.ChooseGenerateRecipe.route) {
            RandomGenerationRecipeScreen(navController = navController)
        }
        composable(MainScreen.DetailHookahScreen.route) {
            DetailHookahScreen(navController = navController)
        }
    }
}

sealed class MainScreen(val route: String) {
    object Home : MainScreen("HOME")
    object Favorite : MainScreen("FAVORITE")
    object Settings : MainScreen("SETTINGS")
    object ChooseGenerateRecipe : MainScreen("CHOOSE_GENERATE_RECIPE")
    object DetailHookahScreen : MainScreen("DETAIL_HOOKAH_SCREEN")
}
