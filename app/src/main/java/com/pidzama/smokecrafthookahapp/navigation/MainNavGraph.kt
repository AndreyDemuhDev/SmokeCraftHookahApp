package com.pidzama.smokecrafthookahapp.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pidzama.smokecrafthookahapp.domain.entities.RecipeModelEntity
import com.pidzama.smokecrafthookahapp.presentation.archive_orders.OrderArchiveScreen
import com.pidzama.smokecrafthookahapp.presentation.current_order.CurrentOrderViewModel
import com.pidzama.smokecrafthookahapp.presentation.current_order.CurrentOrdersScreen
import com.pidzama.smokecrafthookahapp.presentation.detail_hookah.DetailHookahScreen
import com.pidzama.smokecrafthookahapp.presentation.detail_hookah.DetailHookahViewModel
import com.pidzama.smokecrafthookahapp.presentation.detail_order.DetailOrderScreen
import com.pidzama.smokecrafthookahapp.presentation.detail_order.DetailOrderViewModel
import com.pidzama.smokecrafthookahapp.presentation.profile.ProfileScreen
import com.pidzama.smokecrafthookahapp.presentation.profile.ProfileViewModel
import com.pidzama.smokecrafthookahapp.presentation.random_generation_recipe.RandomGenerationRecipeScreen
import com.pidzama.smokecrafthookahapp.presentation.recipe_generation_method.RecipeGenerationMethodScreen
import com.pidzama.smokecrafthookahapp.presentation.recipe_generation_method.RecipeGenerationViewModel


@Composable
fun MainNavGraph(
    navController: NavHostController,
    darkTheme: Boolean, onThemeUpdated: () -> Unit,
    viewModelMain: ProfileViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        route = Graph.MAIN,
        startDestination = MainScreen.CurrentOrders.route,
        modifier = modifier
    ) {
        val idOrder =
            navController.previousBackStackEntry?.savedStateHandle?.get<Int?>("idOrder")
        composable(MainScreen.Profile.route) {
            ProfileScreen(
                navController = navController,
                darkTheme = darkTheme,
                onThemeUpdated = onThemeUpdated,
                viewModel = viewModelMain
            )
        }
        composable(MainScreen.CurrentOrders.route) {
            val viewModel = hiltViewModel<CurrentOrderViewModel>()
            CurrentOrdersScreen(
                viewModel = viewModel,
                navigateToDetailOrder = { id ->
                    navigateWithIdOrder(
                        navController = navController,
                        idOrder = id,
                        routeDestination = MainScreen.DetailOrderScreen.route
                    )
                },
                onClickAddNewOrder = { id ->
                    navigateWithIdOrder(
                        navController = navController,
                        idOrder = id,
                        routeDestination = MainScreen.RecipeGenerationMethod.route
                    )
                },
                navController = navController,
                modifier = Modifier.fillMaxSize()
            )
        }
        composable(MainScreen.RecipeGenerationMethod.route) {
            RecipeGenerationMethodScreen(
                onClickRandomGeneration = {
                    navigateWithIdOrder(
                        idOrder = idOrder ?: 0,
                        navController = navController,
                        routeDestination = MainScreen.ChooseGenerateRecipe.route
                    )
                }
            )
        }
        composable(MainScreen.OrderArchive.route) {
            OrderArchiveScreen()
        }
        composable(MainScreen.ChooseGenerateRecipe.route) {
            val viewModel = hiltViewModel<RecipeGenerationViewModel>()
            RandomGenerationRecipeScreen(navController = navController,
                viewModel = viewModel,
                navigateToDetails = { recipe, idRecipe ->
                    navigateToDetailsRecipe(
                        navController = navController,
                        recipe = recipe,
                        idRecipe = idRecipe
                    )
                })
        }
        composable(route = MainScreen.DetailHookahScreen.route) {
            val viewModelDetailHookah = hiltViewModel<DetailHookahViewModel>()
            val viewModelDetailOrder = hiltViewModel<DetailOrderViewModel>()
            val recipe =
                navController.previousBackStackEntry?.savedStateHandle?.get<RecipeModelEntity?>("recipe")
            if (recipe != null) {
                DetailHookahScreen(
                    navController = navController,
                    recipe = recipe,
                    idOrder=idOrder ?: 1,
                    viewModelDetailHookah = viewModelDetailHookah,
                    viewModelDetailOrder = viewModelDetailOrder
                )
            }
        }
        composable(route = MainScreen.DetailOrderScreen.route) {
            val viewModel = hiltViewModel<DetailOrderViewModel>()
            if (idOrder != null) {
                DetailOrderScreen(
                    id = idOrder,
                    onClickCloseOrder = { navController.navigate(MainScreen.CurrentOrders.route) },
                    onClickAddRecipe = {
                        navigateWithIdOrder(
                            navController = navController,
                            routeDestination = MainScreen.RecipeGenerationMethod.route,
                            idOrder = idOrder
                        )
                    },
                    viewModel = viewModel,
                    navController = navController,
                    modifier = Modifier.fillMaxSize()
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
    object DetailOrderScreen : MainScreen("DETAIL_OREDER_SCREEN")
}

private fun navigateToDetailsRecipe(
    navController: NavController,
    recipe: RecipeModelEntity,
    idRecipe: Int
) {
    navController.currentBackStackEntry?.savedStateHandle?.set("recipe", recipe)
    navController.currentBackStackEntry?.savedStateHandle?.set("idOrder", idRecipe)
    navController.navigate(
        route = MainScreen.DetailHookahScreen.route
    )
}

private fun navigateWithIdOrder(
    navController: NavController,
    routeDestination: String,
    idOrder: Int,
) {
    navController.currentBackStackEntry?.savedStateHandle?.set("idOrder", idOrder)
    navController.navigate(
        route = routeDestination
    )
}

