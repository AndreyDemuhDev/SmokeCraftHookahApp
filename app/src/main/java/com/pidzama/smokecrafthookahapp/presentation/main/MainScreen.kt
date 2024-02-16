package com.pidzama.smokecrafthookahapp.presentation.main

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.BottomNavigation
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.pidzama.smokecrafthookahapp.navigation.MainNavGraph
import com.pidzama.smokecrafthookahapp.presentation.common.BottomBarScreens
import com.pidzama.smokecrafthookahapp.presentation.profile.ProfileViewModel
import com.pidzama.smokecrafthookahapp.ui.theme.dimens

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(
    navController: NavHostController = rememberNavController(),
    darkTheme: Boolean, onThemeUpdated: () -> Unit,
    viewModelMain: ProfileViewModel
) {

    Scaffold(
        bottomBar = { BottomBarApp(navController = navController) },
        backgroundColor = MaterialTheme.colorScheme.background
    ) {
        Surface(
            content = {
                Column {
                    MainNavGraph(
                        navController = navController,
                        darkTheme = darkTheme, onThemeUpdated = onThemeUpdated,
                        viewModelMain = viewModelMain
                    )
                }
            }
        )
    }
}

@Composable
fun BottomBarApp(navController: NavHostController) {
    val screens = listOf(
        BottomBarScreens.Profile,
        BottomBarScreens.CurrentOrders,
        BottomBarScreens.OrderArchive,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomBarDestination = screens.any { it.route == currentDestination?.route }
    if (bottomBarDestination) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height((MaterialTheme.dimens.small1 / 3)),
                color = MaterialTheme.colorScheme.primary
            )
            BottomNavigation {
                screens.forEach { screen ->
                    AddItem(
                        screen = screen,
                        currentDestination = currentDestination,
                        navController = navController
                    )
                }
            }
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreens,
    currentDestination: NavDestination?,
    navController: NavHostController
) {

    NavigationBarItem(
        modifier = Modifier.background(
            MaterialTheme.colorScheme.surface,
        ),
        icon = {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(id = screen.icon),
                    contentDescription = "${screen.title} Icon",
                    tint = MaterialTheme.colorScheme.inverseSurface
                )
                Text(
                    text = screen.title,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.inverseSurface
                )
            }
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        },
        colors = NavigationBarItemDefaults.colors(
            indicatorColor = MaterialTheme.colorScheme.surface,
            selectedTextColor = MaterialTheme.colorScheme.surface
        )
    )
}