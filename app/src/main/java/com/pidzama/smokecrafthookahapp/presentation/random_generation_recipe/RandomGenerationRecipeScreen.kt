package com.pidzama.smokecrafthookahapp.presentation.random_generation_recipe

import android.content.res.Configuration
import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.pidzama.smokecrafthookahapp.R
import com.pidzama.smokecrafthookahapp.data.model.RandomRecipeSubList
import com.pidzama.smokecrafthookahapp.presentation.common.bounceClick
import com.pidzama.smokecrafthookahapp.presentation.current_orders.CurrentOrdersViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import com.pidzama.smokecrafthookahapp.presentation.common.NoRippleEffect
import com.pidzama.smokecrafthookahapp.presentation.current_orders.substringToken
import com.pidzama.smokecrafthookahapp.presentation.random_generation_recipe.common.LandscapeRecipeCard
import com.pidzama.smokecrafthookahapp.presentation.random_generation_recipe.common.PortraitRecipeCard
import com.pidzama.smokecrafthookahapp.ui.theme.ScreenOrientation
import com.pidzama.smokecrafthookahapp.ui.theme.dimens
import com.pidzama.smokecrafthookahapp.utils.Constants.TastyWeight.ListTastyWeight

@Composable
fun RandomGenerationRecipeScreen(
    navController: NavHostController = rememberNavController(),
    navigateToDetails: (RandomRecipeSubList, Int) -> Unit,
    viewModel: CurrentOrdersViewModel
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
                            text = stringResource(id = R.string.recipes),
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.inverseSurface
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
                    IconButton(
                        onClick = { },
                        enabled = true,
                        interactionSource = remember { NoRippleEffect() }) {
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
                )
            } else {
                LandscapeRecipesContentView(
                    navigateToDetails = navigateToDetails,
                    viewModel = viewModel
                )
            }
        }
    )
}

@OptIn(ExperimentalAnimationApi::class, ExperimentalFoundationApi::class)
@Composable
fun PortraitRecipesContentView(
    navigateToDetails: (RandomRecipeSubList, Int) -> Unit,
    viewModel: CurrentOrdersViewModel,
    listTobaccoWeight: List<Float> = ListTastyWeight
) {

    viewModel.getUserToken()
    val userToken = viewModel.token.value
    val updateIndexRecipe = viewModel.data.collectAsState()
    var indexRecipe = updateIndexRecipe.value
    val result = viewModel.res.value
//    LaunchedEffect(key1 = indexRecipe, block = {
//        viewModel.getListRecipes(
//            "Token ${
//                viewModel.token.value.substringToken(
//                    userToken
//                )
//            }"
//        )
//    })

    if (result.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
    if (result.error.isNotEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.error_connection),
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.small2))
            Image(
                painter = painterResource(id = R.drawable.ic_error),
                contentDescription = "error_logo",
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
            )
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
                    items(items = result.data) { recipe ->
                        Card(
                            modifier = Modifier,
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
                        ) {


//
//                            AnimatedContent(
//                                targetState = true,
//                                content = {

                            indexRecipe++
                            val recipeNumber = indexRecipe
                            Log.d("MyLog", "IndexRecipe $indexRecipe")
                            Log.d("MyLog", "РЕЦЕПТ===> $recipe")
                            PortraitRecipeCard(
                                input = recipe,
                                indexRecipe = indexRecipe,
                                listTobaccoWeight = listTobaccoWeight,
                                onClickToDetailsScreen = {
                                    navigateToDetails(
                                        recipe,
                                        recipeNumber
                                    )
                                },
                                radius = MaterialTheme.dimens.radius.value
                            )
//                                },
//                                transitionSpec = {
//                                    scaleIn(
//                                        animationSpec = tween(
//                                            600,
//                                            400
//                                        )
//                                    ) with scaleOut(animationSpec = tween(650))
//                                }
//                            )
                        }
                    }
                }
            }
            Box(modifier = Modifier.weight(0.1f)) {
                Button(
                    onClick = {
//                        count++
                        viewModel.getListRecipes(
                            "Token ${
                                viewModel.token.value.substringToken(
                                    userToken
                                )
                            }"
                        )
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
                        text = stringResource(id = R.string.generate_new_recipe),
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
    navigateToDetails: (RandomRecipeSubList, Int) -> Unit,
    viewModel: CurrentOrdersViewModel,
    listTobaccoWeight: List<Float> = ListTastyWeight
) {

    viewModel.getUserToken()
    val screenHeight = LocalConfiguration.current.screenHeightDp
    val userToken = viewModel.token.value
    val updateIndexRecipe = viewModel.data.collectAsState()
    var indexRecipe = updateIndexRecipe.value
    var result = viewModel.res.value

//    LaunchedEffect(key1 = indexRecipe, block = {
//
//        viewModel.getListRecipies(
//            "Token ${
//                viewModel.token.value.substringToken(
//                    userToken
//                )
//            }"
//        )
//    })
    if (result.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
    if (result.error.isNotEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.error_connection),
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.small2))
            Image(
                painter = painterResource(id = R.drawable.ic_error),
                contentDescription = "error_logo",
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
            )
        }
    }
    if (result.data.isNotEmpty()) {
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
                    items(items = result.data) { recipe ->
                        Card(
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
                        ) {
                            Log.d("MyLog", "IndexRecipe LANDSCAPE $indexRecipe")
                            Log.d("MyLog", "РЕЦЕПТ LANDSCAPE===> $recipe")
//                        AnimatedContent(
//                            targetState = count,
//                            content = {
                            indexRecipe++
                            val recipeNumber = indexRecipe
                            LandscapeRecipeCard(
                                input = recipe,
                                indexRecipe = indexRecipe,
                                listTobaccoWeight = listTobaccoWeight,
                                onClickToDetailsScreen = {
                                    navigateToDetails(
                                        recipe,
                                        recipeNumber
                                    )
                                },
                                radius = MaterialTheme.dimens.radius.value
                            )
//                        LandscapePieChartRecipe(
//                            input = recipe,
//                            indexRecipe = indexRecipe,
//                            listTobaccoWeight = listTobaccoWeight,
//                            onClickToDetailsScreen = {
//                                navigateToDetails(
//                                    recipe,
//                                    recipeNumber
//                                )
//                            }
//                        )
//                            },
//                            transitionSpec = {
//                                scaleIn(
//                                    animationSpec = tween(
//                                        300,
//                                        200
//                                    )
//                                ) with scaleOut(animationSpec = tween(150))
//                            }
//                        )
                        }
                    }
                }
            }
            Box(modifier = Modifier.weight(0.2f)) {
                Button(
                    onClick = {
                        viewModel.getListRecipes(
                            "Token ${
                                viewModel.token.value.substringToken(
                                    userToken
                                )
                            }"
                        )
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
}
