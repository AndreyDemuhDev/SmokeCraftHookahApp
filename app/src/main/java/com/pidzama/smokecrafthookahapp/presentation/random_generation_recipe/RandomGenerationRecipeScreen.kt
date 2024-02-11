package com.pidzama.smokecrafthookahapp.presentation.random_generation_recipe

import android.content.res.Configuration
import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.*
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
import com.pidzama.smokecrafthookahapp.data.model.RandomRecipeSubList
import com.pidzama.smokecrafthookahapp.presentation.common.bounceClick
import com.pidzama.smokecrafthookahapp.presentation.current_orders.CurrentOrdersViewModel
import androidx.compose.foundation.lazy.items
import com.pidzama.smokecrafthookahapp.presentation.current_orders.substringToken
import com.pidzama.smokecrafthookahapp.ui.theme.ScreenOrientation
import com.pidzama.smokecrafthookahapp.ui.theme.dimens
import com.pidzama.smokecrafthookahapp.utils.Constants.TastyWeight.ListTastyWeight
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun RandomGenerationRecipeScreen(
    navController: NavHostController = rememberNavController(),
    navigateToDetails: (RandomRecipeSubList) -> Unit,
    viewModel: CurrentOrdersViewModel
//    darkTheme: Boolean, onThemeUpdated: () -> Unit
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
                PortraitRecipesContentView(
                    navigateToDetails = navigateToDetails,
                    viewModel = viewModel
//                    darkTheme = darkTheme, onThemeUpdated = onThemeUpdated,
                )
            } else {
                LandscapeRecipesContentView(
                    navigateToDetails = navigateToDetails,
                    viewModel = viewModel
//                    darkTheme = darkTheme, onThemeUpdated = onThemeUpdated,
                )
            }
        }
    )
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun PortraitRecipesContentView(
    navigateToDetails: (RandomRecipeSubList) -> Unit,
//    darkTheme: Boolean, onThemeUpdated: () -> Unit,
    viewModel: CurrentOrdersViewModel,
    listTobaccoWeight: List<Float> = ListTastyWeight
) {

    viewModel.getUserToken()
    Log.d("MyLog", "TOKEN----->>>VIEWMODEL ${viewModel.getUserToken()}")
    val listRandomGenerateRecipe = viewModel.generateRecipeList.observeAsState(listOf()).value
    val userToken = viewModel.token.value
    val updateIndexRecipe = viewModel.data.collectAsState()
    var indexRecipe = updateIndexRecipe.value
    var count by remember { mutableStateOf(0) }
    var result = viewModel.res.value

    val clickAction = rememberUpdatedState(newValue = { count++ })
//    viewModel.getMapperRecipe("Token ${
//                viewModel.token.value.substringToken(
//                    userToken
//                )
//            }")
    LaunchedEffect(key1 = count, block = {

        viewModel.getMapperRecipe(
            "Token ${
                viewModel.token.value.substringToken(
                    userToken
                )
            }"
        )
//        launch {
//            viewModel.getListRandomGenerateRecipe("Token ${
//                viewModel.token.value.substringToken(
//                    userToken
//                )
//            }")
//            Log.d("MyLog", "LAUNCHEFFECT")
//            Log.d("MyLog", "TOKEN ${viewModel.token.value.substringToken(userToken)}")
//        }
    })

    if (result.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
    if (result.error.isNotEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = result.error)
        }
    }
    if (result.data.isNotEmpty()) {
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
                    items(result.data,
                        key = { it }
                    ) { recipe ->
                        Card(
                            modifier = Modifier.clickable { navigateToDetails(recipe) },
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
                        ) {
//                            AnimatedContent(
//                                targetState = count,
//                                content = {
                            indexRecipe++
                            Log.d("MyLog", "IndexRecipe $indexRecipe")
                            Log.d("MyLog", "COUNT $count")
                            Log.d("MyLog", "РЕЦЕПТ===> $recipe")
                            PortraitPieChartRecipe(
                                input = recipe,
                                indexRecipe = indexRecipe,
                                listTobaccoWeight = listTobaccoWeight,

                                )
//                                },
//                                transitionSpec = {
//                                    scaleIn(
//                                        animationSpec = tween(
//                                            300,
//                                            200
//                                        )
//                                    ) with scaleOut(animationSpec = tween(150))
//                                }
//                            )
                        }
                    }
                }
            }
            Box(modifier = Modifier.weight(0.1f)) {
                Button(
                    onClick = {
                        clickAction.value.invoke()
//                        count++
                        viewModel.updateRecipesIndex(updateIndexRecipe.value)
                    },
                    modifier = Modifier
                        .bounceClick()
                        .fillMaxWidth()
                        .height(MaterialTheme.dimens.buttonHeight),
                    shape = RoundedCornerShape(MaterialTheme.dimens.cornerShape),
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
}


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun LandscapeRecipesContentView(
    navigateToDetails: (RandomRecipeSubList) -> Unit,
    viewModel: CurrentOrdersViewModel,
//    darkTheme: Boolean, onThemeUpdated: () -> Unit,
    listTobaccoWeight: List<Float> = ListTastyWeight
) {
    viewModel.getUserToken()
    val screenHeight = LocalConfiguration.current.screenHeightDp
    val listRandomGenerateRecipe = viewModel.generateRecipeList.observeAsState().value
    val updateIndexRecipe = viewModel.data.collectAsState()
    var indexRecipe = updateIndexRecipe.value
    val userToken = viewModel.token.value
    var count by remember { mutableStateOf(0) }
    var result = viewModel.res.value

    val clickAction = rememberUpdatedState(newValue = { count++ })

    LaunchedEffect(key1 = count, block = {

        viewModel.getMapperRecipe(
            "Token ${
                viewModel.token.value.substringToken(
                    userToken
                )
            }"
        )
    })

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
                items(items = result.data, key = { it }
                ) { recipe ->
                    Card(
                        modifier = Modifier.clickable { navigateToDetails(recipe) },
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
                    ) {
                        indexRecipe++
                        Log.d("MyLog", "IndexRecipe LANDSCAPE $indexRecipe")
                        Log.d("MyLog", "COUNT LANDSCAPE $count")
                        Log.d("MyLog", "РЕЦЕПТ LANDSCAPE===> $recipe")
                        AnimatedContent(
                            targetState = count,
                            content = {
                                LandscapePieChartRecipe(
                                    input = recipe,
                                    indexRecipe = indexRecipe,
                                    listTobaccoWeight = listTobaccoWeight
                                )
                            },
                            transitionSpec = {
                                scaleIn(
                                    animationSpec = tween(
                                        300,
                                        200
                                    )
                                ) with scaleOut(animationSpec = tween(150))
                            }
                        )
                    }
                }
            }
        }
        Box(modifier = Modifier.weight(0.2f)) {
            Button(
                onClick = {
                    clickAction.value.invoke()
                },
                modifier = Modifier
                    .bounceClick()
                    .fillMaxWidth()
                    .height(MaterialTheme.dimens.buttonHeight)
                    .padding(horizontal = (screenHeight / 25).dp),
                shape = RoundedCornerShape(MaterialTheme.dimens.cornerShape),
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

