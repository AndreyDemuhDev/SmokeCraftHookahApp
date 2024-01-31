package com.pidzama.smokecrafthookahapp.presentation.random_generation_recipe

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.pidzama.smokecrafthookahapp.R
import com.pidzama.smokecrafthookahapp.navigation.MainScreen
import com.pidzama.smokecrafthookahapp.presentation.current_orders.CurrentOrdersViewModel
import com.pidzama.smokecrafthookahapp.ui.theme.ScreenOrientation
import com.pidzama.smokecrafthookahapp.ui.theme.dimens

@Composable
fun RandomGenerationRecipeScreen(
    navController: NavHostController = rememberNavController()
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(),
                        Alignment.Center
                    ) {
                        Text(
                            text = "Рецепты",
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                },
                backgroundColor = MaterialTheme.colorScheme.primary,
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrow_left),
                            contentDescription = "arrow_left"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { }, enabled = true) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrow_left),
                            contentDescription = "arrow_left",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        },
        content = {
            if (ScreenOrientation == Configuration.ORIENTATION_PORTRAIT) {
                RecipesContentView(navController = navController)
            } else {
                LandscapeRecipesContentView(navController = navController)
            }
        }
    )
}

@Composable
fun RecipesContentView(navController: NavHostController) {
    val viewModel = hiltViewModel<CurrentOrdersViewModel>()
    val listRandomGenerateRecipe = viewModel.generateRecipeList.observeAsState(listOf()).value
    val updateIndexRecipe = viewModel.data.collectAsState()
    var indexRecipe = updateIndexRecipe.value
    var updateRecipe = true
    val isLoading = remember { mutableStateOf(false) }
    LaunchedEffect(key1 = updateRecipe) {
        viewModel.getListRandomGenerateRecipe()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = MaterialTheme.dimens.large3,
                start = MaterialTheme.dimens.small3,
                end = MaterialTheme.dimens.small3
            )
    ) {
        Box(modifier = Modifier.weight(0.9f)) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                itemsIndexed(listRandomGenerateRecipe) { index, list ->
                    indexRecipe++
                    Card(modifier = Modifier
                        .clickable {
                            navController.navigate(
                                route = MainScreen.DetailHookahScreen.route + "/$list"
                            )
                        }) {
                        PieChartRecipe(
                            input = list,
                            navController = navController,
                            indexRecipe = indexRecipe,
//                            onClickRecipe = { listRandomGenerateRecipe[1] }
                        )
                    }
                    Log.d("MyLog", "LIST====================> $list")
                }
            }
        }
        Box(modifier = Modifier.weight(0.1f)) {
            Button(
                onClick = {
                    viewModel.getListRandomGenerateRecipe()
                    updateRecipe = false
                    viewModel.updateRecipesIndex(updateIndexRecipe.value)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(MaterialTheme.dimens.buttonHeight),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    text = "Сгенерировать новый рецепт",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}

@Composable
fun LandscapeRecipesContentView(navController: NavHostController) {
    val screenHeight = LocalConfiguration.current.screenHeightDp

    val viewModel = hiltViewModel<CurrentOrdersViewModel>()
    val listRandomGenerateRecipe = viewModel.generateRecipeList.observeAsState(listOf()).value
    val updateIndexRecipe = viewModel.data.collectAsState()
    var indexRecipe = updateIndexRecipe.value
    var updateRecipe = true
    val isLoading = remember { mutableStateOf(false) }
    LaunchedEffect(key1 = updateRecipe) {
        viewModel.getListRandomGenerateRecipe()
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = (screenHeight / 6).dp,
                start = (screenHeight / 20).dp,
                end = (screenHeight / 20).dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.weight(0.9f)) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                itemsIndexed(listRandomGenerateRecipe) { index, list ->
                    indexRecipe++
                    Card(modifier = Modifier
                        .clickable {
                            navController.navigate(
                                route = MainScreen.DetailHookahScreen.route + "/$list"
                            )
                        }) {
                        LandscapePieChartRecipe(
                            input = list,
                            navController = navController,
                            indexRecipe = indexRecipe,
//                            onClickRecipe = { listRandomGenerateRecipe[1] }
                        )
                    }
                    Log.d("MyLog", "LIST====================> $list")
                }
            }
        }
        Box(modifier = Modifier.weight(0.2f)) {
            Button(
                onClick = {
                    viewModel.getListRandomGenerateRecipe()
                    updateRecipe = false
                    viewModel.updateRecipesIndex(updateIndexRecipe.value)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(MaterialTheme.dimens.buttonHeight)
                    .padding(horizontal = (screenHeight/25).dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_refresh),
                    contentDescription = "refresh_icon"
                )
            }
        }
    }
}
