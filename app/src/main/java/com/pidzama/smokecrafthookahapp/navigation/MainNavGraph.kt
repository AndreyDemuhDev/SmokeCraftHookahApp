package com.pidzama.smokecrafthookahapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pidzama.smokecrafthookahapp.data.model.RandomRecipeSubList
import com.pidzama.smokecrafthookahapp.presentation.profile.ProfileScreen
import com.pidzama.smokecrafthookahapp.presentation.detail_hookah.DetailHookahScreen
import com.pidzama.smokecrafthookahapp.presentation.current_orders.CurrentOrders
import com.pidzama.smokecrafthookahapp.presentation.random_generation_recipe.RandomGenerationRecipeScreen
import com.pidzama.smokecrafthookahapp.presentation.archive_orders.OrderArchiveScreen


@Composable
fun MainNavGraph(
    navController: NavHostController,
    darkTheme: Boolean, onThemeUpdated: () -> Unit
) {
    NavHost(
        navController = navController,
        route = Graph.MAIN,
        startDestination = MainScreen.CurrentOrders.route
    ) {
        composable(MainScreen.Profile.route) {
            ProfileScreen(
                navController = navController,
                darkTheme = darkTheme, onThemeUpdated = onThemeUpdated
            )
        }
        composable(MainScreen.CurrentOrders.route) {
            CurrentOrders(navController = navController)
        }
        composable(MainScreen.OrderArchive.route) {
            OrderArchiveScreen(navController = navController)
        }
        composable(MainScreen.ChooseGenerateRecipe.route) {
            RandomGenerationRecipeScreen(navController = navController,
                navigateToDetails = { recipe ->
                    navigateToDetails(
                        navController = navController,
                        recipe = recipe
                    )
                })
        }
        composable(route = MainScreen.DetailHookahScreen.route) {
            navController.previousBackStackEntry?.savedStateHandle?.get<RandomRecipeSubList?>("recipe")
                ?.let { recipe ->
                    DetailHookahScreen(
                        navController = navController,
                        recipe = recipe
                    )
                }
        }
    }
}

sealed class MainScreen(val route: String) {
    object Profile : MainScreen("PROFILE")
    object CurrentOrders : MainScreen("CURRENT_ORDERS")
    object OrderArchive : MainScreen("ORDER_ARCHIVE")
    object ChooseGenerateRecipe : MainScreen("CHOOSE_GENERATE_RECIPE")
    object DetailHookahScreen : MainScreen("DETAIL_HOOKAH_SCREEN")
}

private fun navigateToDetails(navController: NavController, recipe: RandomRecipeSubList) {
    navController.currentBackStackEntry?.savedStateHandle?.set("recipe", recipe)
    navController.navigate(
        route = MainScreen.DetailHookahScreen.route
    )
}