package com.pidzama.smokecrafthookahapp.presentation.random_generation_recipe

import android.content.res.Configuration
import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.lazy.items
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
import com.pidzama.smokecrafthookahapp.data.DataStoreRepository
import com.pidzama.smokecrafthookahapp.data.model.RandomRecipeSubList
import com.pidzama.smokecrafthookahapp.presentation.common.bounceClick
import com.pidzama.smokecrafthookahapp.presentation.current_orders.CurrentOrdersViewModel
import com.pidzama.smokecrafthookahapp.presentation.current_orders.addEnthusiasm
import com.pidzama.smokecrafthookahapp.ui.theme.ScreenOrientation
import com.pidzama.smokecrafthookahapp.ui.theme.dimens
import com.pidzama.smokecrafthookahapp.utils.Constants.TastyWeight.ListTastyWeight
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
//                LandscapeRecipesContentView(
//                    navigateToDetails = navigateToDetails,
////                    darkTheme = darkTheme, onThemeUpdated = onThemeUpdated,
//                )
            }
        }
    )
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun PortraitRecipesContentView(
    navigateToDetails: (RandomRecipeSubList) -> Unit,
//    darkTheme: Boolean, onThemeUpdated: () -> Unit,
    viewModel: CurrentOrdersViewModel = hiltViewModel(),
    listTobaccoWeight: List<Float> = ListTastyWeight
) {
    viewModel.getUserToken()
    val listRandomGenerateRecipe = viewModel.generateRecipeList.observeAsState(listOf()).value
    val userToken = viewModel.token.value
    val updateIndexRecipe = viewModel.data.collectAsState()
    var indexRecipe = updateIndexRecipe.value
    var count by remember {mutableStateOf(0)}

    LaunchedEffect(key1 = count, block = {
        launch {
            viewModel.getListRandomGenerateRecipe("Token ${
                viewModel.token.value.addEnthusiasm(
                    userToken
                )
            }")
            Log.d("MyLog", "LAUNCHEFFECT")
            Log.d("MyLog", "TOKEN ${viewModel.token.value.addEnthusiasm(userToken)}")
        }
    })
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
                itemsIndexed(items = listRandomGenerateRecipe) { _, list ->
                    Card(
                        modifier = Modifier.clickable { navigateToDetails(list) },
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
                    ) {
                        indexRecipe++
                        Log.d("MyLog", "IndexRecipe ${indexRecipe}")
                        Log.d("MyLog", "РЕЦЕПТ----> ${list}")
                        AnimatedContent(
                            targetState = count,
                            content = {
                                indexRecipe++
                                Log.d("MyLog", "IndexRecipe ${indexRecipe}")
                                Log.d("MyLog", "COUNT ${count}")
                                PortraitPieChartRecipe(
                                    input = list,
                                    indexRecipe = indexRecipe,
                                    listTobaccoWeight = listTobaccoWeight,

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
        Box(modifier = Modifier.weight(0.1f)) {
            Button(
                onClick = {
                    count++
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
//    darkTheme: Boolean, onThemeUpdated: () -> Unit,
    listTobaccoWeight: List<Float> = ListTastyWeight
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp
    val viewModel = hiltViewModel<CurrentOrdersViewModel>()
    val listRandomGenerateRecipe = viewModel.generateRecipeList.observeAsState().value
    val updateIndexRecipe = viewModel.data.collectAsState()
    var indexRecipe = updateIndexRecipe.value

    var count by remember {
        mutableStateOf(0)
    }
    LaunchedEffect(key1 = count) {
//        viewModel.getListRandomGenerateRecipe("Token 3d3d1527f722c271e123816778222b97873805c1")
//        viewModel.getListRandomGenerateRecipe("Token 3d3d1527f722c271e123816778222b97873805c1")
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


//        Box(modifier = Modifier.weight(0.9f)) {
//            LazyColumn(
//                modifier = Modifier
//                    .fillMaxSize()
//            ) {
//                if (listRandomGenerateRecipe != null) {
//                    itemsIndexed(listRandomGenerateRecipe.listRandomRecipe) { _, list ->
//                        Card(
        //                        modifier = Modifier.clickable { navigateToDetails(list) },
//                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
//                        ) {
//                            indexRecipe++
//                            AnimatedContent(
//                                targetState = count,
//                                content = {
        //                                LandscapePieChartRecipe(
        //                                    input = list,
        //                                    indexRecipe = indexRecipe,
        //                                    listTobaccoWeight = listTobaccoWeight
        //                                )
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
//                        }
//                    }
//                }
//            }
//        }
        Box(modifier = Modifier.weight(0.2f)) {
            Button(
                onClick = {
                    count++
//                    viewModel.getListRandomGenerateRecipe()
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
