package com.pidzama.smokecrafthookahapp.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pidzama.smokecrafthookahapp.data.model.generate_model.ModelRecipeItem
import com.pidzama.smokecrafthookahapp.presentation.archive_orders.OrderArchiveScreen
import com.pidzama.smokecrafthookahapp.presentation.create_order.CurrentOrders
import com.pidzama.smokecrafthookahapp.presentation.detail_hookah.DetailHookahScreen
import com.pidzama.smokecrafthookahapp.presentation.detail_hookah.DetailHookahViewModel
import com.pidzama.smokecrafthookahapp.presentation.profile.ProfileScreen
import com.pidzama.smokecrafthookahapp.presentation.profile.ProfileViewModel
import com.pidzama.smokecrafthookahapp.presentation.random_generation_recipe.RandomGenerationRecipeScreen
import com.pidzama.smokecrafthookahapp.presentation.recipe_generation_method.RecipeGenerationMethodScreen
import com.pidzama.smokecrafthookahapp.presentation.recipe_generation_method.RecipeGenerationViewModel


@Composable
fun MainNavGraph(
    navController: NavHostController,
    darkTheme: Boolean, onThemeUpdated: () -> Unit,
    viewModelMain: ProfileViewModel
) {
    NavHost(
        navController = navController,
        route = Graph.MAIN,
        startDestination = MainScreen.CurrentOrders.route
    ) {
        composable(MainScreen.Profile.route) {
            ProfileScreen(
                navController = navController,
                darkTheme = darkTheme,
                onThemeUpdated = onThemeUpdated,
                viewModel = viewModelMain
            )
        }
        composable(MainScreen.CurrentOrders.route) {
            CurrentOrders(
                navController = navController
            )
        }
        composable(MainScreen.RecipeGenerationMethod.route) {
            RecipeGenerationMethodScreen(
                navController = navController,
            )
        }
        composable(MainScreen.OrderArchive.route) {
            OrderArchiveScreen()
        }
        composable(MainScreen.ChooseGenerateRecipe.route) {
            val viewModel = hiltViewModel<RecipeGenerationViewModel>()
            RandomGenerationRecipeScreen(navController = navController,
                viewModel = viewModel,
                navigateToDetails = { recipe, numberRecipe ->
                    navigateToDetails(
                        navController = navController,
                        recipe = recipe,
                        numberRecipe = numberRecipe
                    )
                })
        }
        composable(route = MainScreen.DetailHookahScreen.route) {
            val viewModel = hiltViewModel<DetailHookahViewModel>()
            val recipe =
                navController.previousBackStackEntry?.savedStateHandle?.get<ModelRecipeItem?>("recipe")
            val recipeNumber =
                navController.previousBackStackEntry?.savedStateHandle?.get<Int?>("numberRecipe")
            if (recipe != null && recipeNumber != null) {
                DetailHookahScreen(
                    navController = navController,
                    recipe = recipe,
                    numberRecipe = recipeNumber,
                    viewModel = viewModel
                )
            }
        }
    }
}

sealed class MainScreen(val route: String) {
    object Profile : MainScreen("PROFILE")
    object CurrentOrders : MainScreen("CURRENT_ORDERS")
    object RecipeGenerationMethod : MainScreen("RECIPE_GENERATION_METHOD")
    object OrderArchive : MainScreen("ORDER_ARCHIVE")
    object ChooseGenerateRecipe : MainScreen("CHOOSE_GENERATE_RECIPE")
    object DetailHookahScreen : MainScreen("DETAIL_HOOKAH_SCREEN")
}

private fun navigateToDetails(
    navController: NavController,
    recipe: ModelRecipeItem,
    numberRecipe: Int
) {
    navController.currentBackStackEntry?.savedStateHandle?.set("recipe", recipe)
    navController.currentBackStackEntry?.savedStateHandle?.set("numberRecipe", numberRecipe)
    navController.navigate(
        route = MainScreen.DetailHookahScreen.route
    )
}