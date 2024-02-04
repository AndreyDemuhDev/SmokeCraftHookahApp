package com.pidzama.smokecrafthookahapp.presentation.random_generation_recipe

import android.content.res.Configuration
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
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
import com.pidzama.smokecrafthookahapp.ui.theme.ScreenOrientation
import com.pidzama.smokecrafthookahapp.ui.theme.dimens
import com.pidzama.smokecrafthookahapp.utils.Constants.TastyWeight.ListTastyWeight

@Composable
fun RandomGenerationRecipeScreen(
    navController: NavHostController = rememberNavController(),
    navigateToDetails: (RandomRecipeSubList) -> Unit
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
                    navigateToDetails = navigateToDetails
                )
            } else {
                LandscapeRecipesContentView(
                    navigateToDetails = navigateToDetails
                )
            }
        }
    )
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun PortraitRecipesContentView(
    navigateToDetails: (RandomRecipeSubList) -> Unit,
    listTobaccoWeight: List<Float> = ListTastyWeight
) {
    val viewModel = hiltViewModel<CurrentOrdersViewModel>()
    val listRandomGenerateRecipe = viewModel.generateRecipeList.observeAsState(listOf()).value
    val updateIndexRecipe = viewModel.data.collectAsState()
    var indexRecipe = updateIndexRecipe.value
    LaunchedEffect(key1 = true) {
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
        var isVisible by remember {
            mutableStateOf(true)
        }
        Box(modifier = Modifier.weight(0.9f)) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                itemsIndexed(listRandomGenerateRecipe) { _, list ->
                    indexRecipe++
                    Card(
                        modifier = Modifier.clickable { navigateToDetails(list) },
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
                    ) {
                        AnimatedContent(
                            targetState = isVisible,
                            content = { isVisible2 ->
                                if (isVisible2) {
                                    PortraitPieChartRecipe(
                                        input = list,
                                        indexRecipe = indexRecipe,
                                        listTobaccoWeight = listTobaccoWeight
                                    )
                                } else {
                                    PortraitPieChartRecipe(
                                        input = list,
                                        indexRecipe = indexRecipe,
                                        listTobaccoWeight = listTobaccoWeight
                                    )
                                }
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
        Box(modifier = Modifier.weight(0.1f)) {
            Button(
                onClick = {
                    isVisible=!isVisible
                    viewModel.getListRandomGenerateRecipe()
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

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun LandscapeRecipesContentView(
    navigateToDetails: (RandomRecipeSubList) -> Unit,
    listTobaccoWeight: List<Float> = ListTastyWeight
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp
    val viewModel = hiltViewModel<CurrentOrdersViewModel>()
    val listRandomGenerateRecipe = viewModel.generateRecipeList.observeAsState(listOf()).value
    val updateIndexRecipe = viewModel.data.collectAsState()
    var indexRecipe = updateIndexRecipe.value
    LaunchedEffect(key1 = true) {
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
        var isVisible by remember {
            mutableStateOf(true)
        }


        Box(modifier = Modifier.weight(0.9f)) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                itemsIndexed(listRandomGenerateRecipe) { _, list ->
                    indexRecipe++
                    Card(
                        modifier = Modifier.clickable { navigateToDetails(list) },
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
                    ) {
                        AnimatedContent(
                            targetState = isVisible,
                            content = { isVisible2 ->
                                if (isVisible2) {
                                    LandscapePieChartRecipe(
                                        input = list,
                                        indexRecipe = indexRecipe,
                                        listTobaccoWeight = listTobaccoWeight
                                    )
                                } else {
                                    LandscapePieChartRecipe(
                                        input = list,
                                        indexRecipe = indexRecipe,
                                        listTobaccoWeight = listTobaccoWeight
                                    )
                                }
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
                    isVisible = !isVisible
                    viewModel.getListRandomGenerateRecipe()
                    viewModel.updateRecipesIndex(updateIndexRecipe.value)
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
