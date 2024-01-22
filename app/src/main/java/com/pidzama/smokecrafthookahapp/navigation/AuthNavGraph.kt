package com.pidzama.smokecrafthookahapp.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.pidzama.smokecrafthookahapp.presentation.auth.AuthScreens

fun NavGraphBuilder.authNavGraph(navController: NavHostController){
    navigation(
        route = Graph.AUTH,
        startDestination =AuthScreen.SignUp.route
    ){
        composable(route = AuthScreen.SignUp.route){
            AuthScreens(navController = navController)
        }
        composable(route = AuthScreen.SignIn.route){
//            SignInScreen(navController = navController)
        }
    }
}

sealed class AuthScreen(val route: String) {
    object SignIn : AuthScreen("SIGN_IN")
    object SignUp : AuthScreen("SIGN_UP")
}