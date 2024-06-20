package com.pidzama.smokecrafthookahapp.presentation.detail_hookah

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.pidzama.smokecrafthookahapp.R
import com.pidzama.smokecrafthookahapp.data.model.generate_model.MatchedTobacco
import com.pidzama.smokecrafthookahapp.data.model.generate_model.ModelRecipeItem
import com.pidzama.smokecrafthookahapp.data.model.generate_model.Taste
import com.pidzama.smokecrafthookahapp.data.remote.order.OrderRequest
import com.pidzama.smokecrafthookahapp.data.remote.reduce.ReduceRecipeRequest
import com.pidzama.smokecrafthookahapp.navigation.Graph
import com.pidzama.smokecrafthookahapp.navigation.MainScreen
import com.pidzama.smokecrafthookahapp.presentation.common.DetailsRecipeItemCard
import com.pidzama.smokecrafthookahapp.presentation.common.TopBarContent
import com.pidzama.smokecrafthookahapp.presentation.common.bounceClick
import com.pidzama.smokecrafthookahapp.presentation.detail_order.DetailOrderViewModel
import com.pidzama.smokecrafthookahapp.ui.theme.ScreenOrientation
import com.pidzama.smokecrafthookahapp.ui.theme.dimens
import com.pidzama.smokecrafthookahapp.utils.convertWeightToInt
import com.talhafaki.composablesweettoast.util.SweetToastUtil.SweetSuccess


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailHookahScreen(
    navController: NavHostController = rememberNavController(),
    recipe: ModelRecipeItem,
    viewModelDetailHookah: DetailHookahViewModel,
    viewModelDetailOrder: DetailOrderViewModel
) {

    val reduceRecipe = emptyList<ReduceRecipeRequest>().toMutableList()

    val listRecipesOnOrder = listOf(
        ModelRecipeItem(
            name = "Клубника",
            taste = listOf(
                Taste(
                    name = "Клубника",
                    weight = 70.0
                ),
                Taste(
                    name = "Мороженое",
                    weight = 30.0
                )
            ),
            matched_tobaccos = listOf(
                MatchedTobacco(
                    brand = "brand 1",
                    created = "create",
                    id = 1,
                    is_active = true,
                    organization = 1,
                    taste = "Клубника",
                    taste_group = "Фруктовые",
                    updated = "update",
                    weight = 10
                ),
                MatchedTobacco(
                    brand = "brand 2",
                    created = "create",
                    id = 2,
                    is_active = true,
                    organization = 2,
                    taste = "Манго",
                    taste_group = "Ягодные",
                    updated = "update",
                    weight = 10
                )
            )
        )
    )

    recipe.matched_tobaccos.forEachIndexed { index, tobacco ->
        reduceRecipe.add(
            ReduceRecipeRequest(
                recipe.matched_tobaccos[index].id,
                convertWeightToInt((recipe.taste[index].weight))
            )
        )
    }
    Log.d("MyLog", "reduceRecipe $reduceRecipe")

    Scaffold(
        topBar = {
            TopBarContent(
                navController = navController,
                canNavigateBack = true,
                title = R.string.orders
            )
        },
        content = {
            if (ScreenOrientation == Configuration.ORIENTATION_PORTRAIT) {
                PortraitDetailView(
                    navController = navController,
                    recipe = recipe,
                    listRecipes =listRecipesOnOrder,
                    reduceRecipe = reduceRecipe,
                    viewModel = viewModelDetailHookah
                )
            } else {
                LandscapeDetailView(
                    navController = navController,
                    recipe = recipe,
                    listRecipes =listRecipesOnOrder,
                    viewModel = viewModelDetailHookah
                )
            }
        }
    )
}


@Composable
fun PortraitDetailView(
    navController: NavHostController,
    recipe: ModelRecipeItem,
    reduceRecipe: List<ReduceRecipeRequest>,
    listRecipes: List<ModelRecipeItem>,
    viewModel: DetailHookahViewModel,
) {

    val screenWidth = LocalConfiguration.current.screenWidthDp
    val screenHeight = LocalConfiguration.current.screenHeightDp
    var openDialogSuccess by remember { mutableStateOf(false) }
    if (openDialogSuccess) {
        openDialogSuccess = false
        SweetSuccess(
            message = stringResource(id = R.string.successfully_written_off),
            duration = Toast.LENGTH_SHORT,
            padding = PaddingValues(top = MaterialTheme.dimens.medium1),
            contentAlignment = Alignment.TopCenter
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = MaterialTheme.dimens.large2,
                start = (screenHeight / 26).dp,
                end = (screenHeight / 26).dp
            ),
    ) {
        Box(
            modifier = Modifier.weight(0.8f)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(MaterialTheme.dimens.small3),
                    text = "${stringResource(id = R.string.order)} №${(1..20).random()}/ ${
                        stringResource(
                            id = R.string.table
                        )
                    } №${(1..8).random()}",
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.inverseSurface,
                    style = MaterialTheme.typography.headlineMedium,
                )
                DetailsRecipeItemCard(
                    input = recipe,
                    isLandscape = false
                )
            }
        }
        BoxWithConstraints(modifier = Modifier.weight(0.2f)) {
            Column(modifier = Modifier.padding(vertical = MaterialTheme.dimens.extraSmall)) {
                Button(
                    onClick = {
                        openDialogSuccess = true
                        viewModel.reduceRecipe(recipe = reduceRecipe)
                        viewModel.createOrder(
                            order = OrderRequest(
                                is_active = true,
                                table_number = 3,
                                hookah_count = 1,
                                recipes = listRecipes
                            )
                        )
                        navController.navigate(MainScreen.CurrentOrders.route) {
                            popUpTo(Graph.MAIN)
                            launchSingleTop = true
                        }
                    },
                    modifier = Modifier
                        .bounceClick()
                        .fillMaxWidth()
                        .height((screenWidth / 8).dp),
                    shape = RoundedCornerShape(MaterialTheme.dimens.cornerShape),
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
                ) {
                    Text(
                        text = stringResource(id = R.string.write_off),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                }
                Spacer(modifier = Modifier.height(MaterialTheme.dimens.small1))
                OutlinedButton(
                    onClick = { navController.popBackStack(MainScreen.CurrentOrders.route, false) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height((screenWidth / 8).dp),
                    shape = RoundedCornerShape(MaterialTheme.dimens.cornerShape),
                    border = BorderStroke(2.dp, color = MaterialTheme.colorScheme.primary),
                ) {
                    Text(
                        text = stringResource(id = R.string.cancel),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}


@Composable
fun LandscapeDetailView(
    navController: NavHostController,
    recipe: ModelRecipeItem,
    listRecipes: List<ModelRecipeItem>,
    viewModel: DetailHookahViewModel,
) {

    val reduceRecipe = mutableListOf<ReduceRecipeRequest>()

    recipe.matched_tobaccos.forEachIndexed { index, matchedTobacco ->
        reduceRecipe.add(
            ReduceRecipeRequest(
                id = matchedTobacco.id,
                weight = convertWeightToInt(recipe.taste[index].weight)
            )
        )
    }

    var openDialogSuccess by remember { mutableStateOf(false) }

    if (openDialogSuccess) {
        openDialogSuccess = false
        SweetSuccess(
            message = stringResource(id = R.string.successfully_written_off),
            duration = Toast.LENGTH_SHORT,
            padding = PaddingValues(top = MaterialTheme.dimens.medium1),
            contentAlignment = Alignment.TopCenter
        )
    }
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = MaterialTheme.dimens.small3,
                start = MaterialTheme.dimens.small3,
                end = MaterialTheme.dimens.small2,
            ),
    ) {
        Box(
            modifier = Modifier.weight(1.2f)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.padding(MaterialTheme.dimens.small1))
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(MaterialTheme.dimens.small3),
                    text = "${stringResource(id = R.string.order)} №${(1..20).random()}/ ${
                        stringResource(
                            id = R.string.table
                        )
                    } №${(1..8).random()}",
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.inverseSurface,
                    style = MaterialTheme.typography.titleLarge,
                )
                DetailsRecipeItemCard(input = recipe, isLandscape = true)
            }
        }
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .weight(0.2f),
            contentAlignment = Alignment.Center
        ) {
            Column(modifier = Modifier.padding(all = MaterialTheme.dimens.extraSmall)) {
                Button(
                    onClick = {
                        viewModel.reduceRecipe(recipe = reduceRecipe)
                        viewModel.createOrder(
                            order = OrderRequest(
                                is_active = true,
                                table_number = 1,
                                hookah_count = 4,
                                recipes = listRecipes
                            )
                        )
                        navController.navigate(MainScreen.CurrentOrders.route)
                        openDialogSuccess = true
                    },
                    modifier = Modifier
                        .bounceClick()
                        .fillMaxWidth()
                        .height(MaterialTheme.dimens.buttonHeight),
                    shape = RoundedCornerShape(MaterialTheme.dimens.cornerShape),
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_apply),
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.inverseSurface),
                        contentDescription = "write_order_icon"
                    )
                }
                Spacer(modifier = Modifier.height(MaterialTheme.dimens.small1))
                OutlinedButton(
                    onClick = { navController.popBackStack(MainScreen.CurrentOrders.route, false) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(MaterialTheme.dimens.buttonHeight),
                    shape = RoundedCornerShape(MaterialTheme.dimens.cornerShape),
                    border = BorderStroke(2.dp, color = MaterialTheme.colorScheme.primary),
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_exit_back),
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                        contentDescription = "write_order_icon"
                    )
                }
            }
        }
    }
}



