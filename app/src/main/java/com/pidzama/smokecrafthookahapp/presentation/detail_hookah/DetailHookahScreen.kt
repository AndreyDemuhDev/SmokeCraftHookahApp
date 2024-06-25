package com.pidzama.smokecrafthookahapp.presentation.detail_hookah

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.pidzama.smokecrafthookahapp.R
import com.pidzama.smokecrafthookahapp.data.remote.order.OrderRequest
import com.pidzama.smokecrafthookahapp.data.remote.reduce.ReduceRecipeRequest
import com.pidzama.smokecrafthookahapp.domain.entities.RecipeModelEntity
import com.pidzama.smokecrafthookahapp.navigation.Graph
import com.pidzama.smokecrafthookahapp.navigation.MainScreen
import com.pidzama.smokecrafthookahapp.presentation.common.DetailsRecipeItemCard
import com.pidzama.smokecrafthookahapp.presentation.common.TopBarContent
import com.pidzama.smokecrafthookahapp.presentation.common.bounceClick
import com.pidzama.smokecrafthookahapp.ui.theme.ScreenOrientation
import com.pidzama.smokecrafthookahapp.ui.theme.dimens
import com.pidzama.smokecrafthookahapp.utils.convertWeightToInt
import com.talhafaki.composablesweettoast.util.SweetToastUtil.SweetSuccess


@Composable
fun DetailHookahScreen(
    navController: NavHostController = rememberNavController(),
    recipe: RecipeModelEntity,
    idOrder: Int,
    viewModelDetailHookah: DetailHookahViewModel,
//    viewModelDetailOrder: DetailOrderViewModel,
) {
    viewModelDetailHookah.getInfoHookah(recipe, idOrder)


    val reduceRecipe = emptyList<ReduceRecipeRequest>().toMutableList()

    recipe.tasteModel.forEachIndexed { index, tobacco ->
        reduceRecipe.add(
            ReduceRecipeRequest(
                recipe.tasteModel[index].idTaste,
                convertWeightToInt((tobacco.weightTaste))
            )
        )
    }


    Log.d("MyLog", "reduce recipe $reduceRecipe")


    val state by viewModelDetailHookah.detailHookahState.collectAsState()

    Scaffold(
        topBar = {
            TopBarContent(
                onClickBack = { navController.popBackStack() },
                canNavigateBack = true,
                title = R.string.orders
            )
        },
        content = { innerPadding ->
            DetailHookahState(
                navController = navController,
                viewModelDetailHookah = viewModelDetailHookah,
                state = state,
//                recipe = recipe,
                reduceRecipe = reduceRecipe,
                onClickCancel = {
                    navController.popBackStack(MainScreen.CurrentOrders.route, false)
                },
//                onClickReduceRecipe = {
//                    viewModelDetailHookah.reduceRecipe(recipe = reduceRecipe)
//                    viewModelDetailHookah.createOrder(
//                        order = OrderRequest(
//                            is_active = true,
//                            table_number = idOrder,
//                            hookah_count = recipe.tasteModel.size,
//                            recipes = listOf(recipe)
//                        )
//                    )
//                    navController.navigate(MainScreen.CurrentOrders.route) {
//                        popUpTo(Graph.MAIN)
//                        launchSingleTop = true
//                    }
//                },
                recipe = recipe,
                idOrder = idOrder,
                modifier = Modifier.padding(innerPadding)
            )
        }
    )
}


@Composable
fun PortraitDetailUpdateOrderView(
    navController: NavHostController,
    recipe: RecipeModelEntity,
    hookahCount: Int,
    tableNumber: Int,
    idOrder: Int,
    reduceRecipe: List<ReduceRecipeRequest>,
    listOrderRecipe: List<RecipeModelEntity>,
    onClickCancel: () -> Unit,
//    onClickReduceRecipe: (List<ReduceRecipeRequest>, List<RecipeModelEntity>) -> Unit,
    viewModelDetailHookah: DetailHookahViewModel,
    modifier: Modifier = Modifier,
) {

    val screenWidth = LocalConfiguration.current.screenWidthDp
//    val screenHeight = LocalConfiguration.current.screenHeightDp
    var openDialogSuccess by remember { mutableStateOf(false) }

    Log.d("MyLog", "ID ORDER $idOrder")
    Log.d("MyLog", "list order RECIPE $listOrderRecipe")
    Log.d("MyLog", "Recipe in screen==>> $recipe")
    Log.d("MyLog", "РЕЦЕПТ на СПИСАНИЕ==>> $reduceRecipe")


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
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = MaterialTheme.dimens.small1),
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
                    text = "${stringResource(id = R.string.table)} №$tableNumber / ${
                        stringResource(
                            id = R.string.order
                        )
                    } №$idOrder",
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
//                        onClickReduceRecipe(reduceRecipe, listOrderRecipe)
                        viewModelDetailHookah.reduceRecipe(recipe = reduceRecipe)
                        viewModelDetailHookah.updateOrder(
                            id = idOrder,
                            recipes = OrderRequest(
                                is_active = true,
                                table_number = tableNumber,
                                hookah_count = hookahCount,
                                recipes = listOrderRecipe
                            )
                        )
                        navController.navigate(MainScreen.CurrentOrders.route) {
                            popUpTo(Graph.MAIN)
                            launchSingleTop = true
                        }
                    },
//                    enabled = numberTable.isNotEmpty(),
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
                    onClick = {
                        onClickCancel()
//                        navController.popBackStack(MainScreen.CurrentOrders.route, false)
                    },
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
fun PortraitDetailCreateOrderView(
    navController: NavHostController,
    recipe: RecipeModelEntity,
    reduceRecipe: List<ReduceRecipeRequest>,
    listOrderRecipe: List<RecipeModelEntity>,
    onClickCancel: () -> Unit,
//    onClickReduceRecipe: (List<ReduceRecipeRequest>, List<RecipeModelEntity>) -> Unit,
    viewModelDetailHookah: DetailHookahViewModel,
    modifier: Modifier = Modifier,
) {

    val screenWidth = LocalConfiguration.current.screenWidthDp
//    val screenHeight = LocalConfiguration.current.screenHeightDp
    var openDialogSuccess by remember { mutableStateOf(false) }
    var numberTable by rememberSaveable { mutableStateOf("5") }

    Log.d("MyLog", "list order RECIPE $listOrderRecipe")
    Log.d("MyLog", "Recipe in screen==>> $recipe")

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
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = MaterialTheme.dimens.small1),
    ) {
        Box(
            modifier = Modifier.weight(0.8f)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = numberTable,
                    onValueChange = {
                        numberTable = it
                    },
                    textStyle = LocalTextStyle.current.copy(color = MaterialTheme.colorScheme.inverseSurface),
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.enter_number_table),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onTertiary
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.onSecondary,
                        unfocusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Number,
                    ),
                    singleLine = true,
                    maxLines = 1,
                    shape = RoundedCornerShape(MaterialTheme.dimens.cornerShape),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(MaterialTheme.dimens.buttonHeight)
                        .width(MaterialTheme.dimens.buttonWidth)
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
//                        onClickReduceRecipe(reduceRecipe, listOrderRecipe)
                        viewModelDetailHookah.reduceRecipe(recipe = reduceRecipe)
                        viewModelDetailHookah.createOrder(
                            order = OrderRequest(
                                is_active = true,
                                table_number = numberTable.toInt(),
                                hookah_count = 1,
                                recipes = listOrderRecipe
                            )
                        )
                        navController.navigate(MainScreen.CurrentOrders.route) {
                            popUpTo(Graph.MAIN)
                            launchSingleTop = true
                        }
                    },
//                    enabled = numberTable.isNotEmpty(),
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
                    onClick = {
                        onClickCancel()
//                        navController.popBackStack(MainScreen.CurrentOrders.route, false)
                    },
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
fun LandscapeDetailUpdateOrderView(
//    navController: NavHostController,
    recipe: RecipeModelEntity,
    idOrder: Int,
//    reduceRecipe: List<ReduceRecipeRequest>,
    listOrderRecipe: List<RecipeModelEntity>,
    viewModelDetailHookah: DetailHookahViewModel,
    onClickCancel: () -> Unit,
//    onClickReduceRecipe: (List<ReduceRecipeRequest>, List<RecipeModelEntity>) -> Unit,
    modifier: Modifier = Modifier,
) {

    var openDialogSuccess by remember { mutableStateOf(false) }

    var numberTable by rememberSaveable { mutableStateOf("") }

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
        modifier = modifier
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
                if (idOrder == 0) {
                    OutlinedTextField(
                        value = numberTable,
                        onValueChange = {
                            numberTable = it
                        },
                        textStyle = LocalTextStyle.current.copy(color = MaterialTheme.colorScheme.inverseSurface),
                        placeholder = {
                            Text(
                                text = stringResource(id = R.string.enter_number_table),
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onTertiary
                            )
                        },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.onSecondary,
                            unfocusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer
                        ),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.Number
                        ),

                        singleLine = true,
                        maxLines = 1,
                        shape = RoundedCornerShape(MaterialTheme.dimens.cornerShape),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(MaterialTheme.dimens.buttonHeight)
                            .width(MaterialTheme.dimens.buttonWidth)
                    )
                } else {
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
//                        onClickReduceRecipe(reduceRecipe, listOrderRecipe)
//                        viewModelDetailHookah.reduceRecipe(recipe = reduceRecipe)
//                        viewModelDetailHookah.createOrder(
//                            order = OrderRequest(
//                                is_active = true,
//                                table_number = 1,
//                                hookah_count = recipe.tasteModel.size,
//                                recipes = listOf(recipe)
//                            )
//                        )
//                        navController.navigate(MainScreen.CurrentOrders.route)
                        openDialogSuccess = true
                    },
                    enabled = numberTable.isNotEmpty(),
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
                    onClick = {
//                        navController.popBackStack(MainScreen.CurrentOrders.route, false)
                        onClickCancel()
                    },
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


@Composable
fun LandscapeDetailCreateOrderView(
    navController: NavHostController,
    recipe: RecipeModelEntity,
    reduceRecipe: List<ReduceRecipeRequest>,
    listOrderRecipe: List<RecipeModelEntity>,
    viewModelDetailHookah: DetailHookahViewModel,
    onClickCancel: () -> Unit,
//    onClickReduceRecipe: (List<ReduceRecipeRequest>, List<RecipeModelEntity>) -> Unit,
    modifier: Modifier = Modifier,
) {

    var openDialogSuccess by remember { mutableStateOf(false) }

    var numberTable by rememberSaveable { mutableStateOf("") }

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
        modifier = modifier
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
                OutlinedTextField(
                    value = numberTable,
                    onValueChange = {
                        numberTable = it
                    },
                    textStyle = LocalTextStyle.current.copy(color = MaterialTheme.colorScheme.inverseSurface),
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.enter_number_table),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onTertiary
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.onSecondary,
                        unfocusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Number
                    ),

                    singleLine = true,
                    maxLines = 1,
                    shape = RoundedCornerShape(MaterialTheme.dimens.cornerShape),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(MaterialTheme.dimens.buttonHeight)
                        .width(MaterialTheme.dimens.buttonWidth)
                )
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
//                        onClickReduceRecipe(reduceRecipe, listOrderRecipe)
                        viewModelDetailHookah.reduceRecipe(recipe = reduceRecipe)
                        viewModelDetailHookah.createOrder(
                            order = OrderRequest(
                                is_active = true,
                                table_number = numberTable.toInt(),
                                hookah_count = 1,
                                recipes = listOf(recipe)
                            )
                        )
                        navController.navigate(MainScreen.CurrentOrders.route)
                        openDialogSuccess = true
                    },
                    enabled = numberTable.isNotEmpty(),
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
                    onClick = {
//                        navController.popBackStack(MainScreen.CurrentOrders.route, false)
                        onClickCancel()
                    },
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

@Composable
fun DetailHookahState(
    viewModelDetailHookah: DetailHookahViewModel,
    state: DetailHookahState,
    recipe: RecipeModelEntity,
//    listOrderRecipe: List<RecipeModelEntity>,
    onClickCancel: () -> Unit,
//    onClickReduceRecipe: (List<ReduceRecipeRequest>, List<RecipeModelEntity>) -> Unit,
    reduceRecipe: List<ReduceRecipeRequest>,
    idOrder: Int,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    val screenModifier = Modifier
        .fillMaxSize()
        .padding(contentPadding)
        .then(modifier)
    when (state) {
        is DetailHookahState.ContentRecipeWithOrder -> {
            DetailHookahScreenContentRecipeWithOrder(
                state = state,
                viewModelDetailHookah = viewModelDetailHookah,
                recipe = recipe,
                idOrder = idOrder,
                reduceRecipe = reduceRecipe,
                navController = navController,
//                listOrderRecipe = listOrderRecipe,
                onClickCancel = onClickCancel,
//                onClickReduceRecipe = onClickReduceRecipe,
                modifier = screenModifier,
            )
        }

        is DetailHookahState.ContentOnlyRecipe -> {
            DetailHookahScreenContentOnlyRecipe(
                state = state,
                viewModelDetailHookah = viewModelDetailHookah,
                recipe = recipe,
                reduceRecipe = reduceRecipe,
                navController = navController,
//                listOrderRecipe = listOrderRecipe,
                onClickCancel = onClickCancel,
//                onClickReduceRecipe = onClickReduceRecipe,
                modifier = screenModifier,
            )
        }

        is DetailHookahState.Error -> {
            DetailHookahScreenError(state = state, modifier = screenModifier)
        }

        is DetailHookahState.Loading -> {
            DetailHookahScreenLoading(state = state, modifier = screenModifier)
        }
    }

}

@Composable
fun DetailHookahScreenContentRecipeWithOrder(
    state: DetailHookahState.ContentRecipeWithOrder,
    viewModelDetailHookah: DetailHookahViewModel,
    onClickCancel: () -> Unit,
//    onClickReduceRecipe: (List<ReduceRecipeRequest>, List<RecipeModelEntity>) -> Unit,
    recipe: RecipeModelEntity,
//    listOrderRecipe: List<RecipeModelEntity>,
    idOrder: Int,
    reduceRecipe: List<ReduceRecipeRequest>,
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {


    val recipeInScreen = state.data
    val listRecipesInOrder = listOf(recipe).toMutableList()
    val currentOrder = state.orderData.recipes.toMutableList()
    val hookahCount = state.orderData.recipes.size
    val tableNumber = state.orderData.table_number
    listRecipesInOrder.addAll(currentOrder)


    Log.d("MyLog", "Текущий ЗАКАЗ через ЗАКАЗ ====> $currentOrder")
    Log.d(
        "MyLog",
        "Обновленный лист заказа со всеми рецептами через ЗАКАЗ ====> $listRecipesInOrder"
    )


    if (ScreenOrientation == Configuration.ORIENTATION_PORTRAIT) {
        PortraitDetailUpdateOrderView(
            navController = navController,
            recipe = recipeInScreen,
            hookahCount = hookahCount,
            tableNumber = tableNumber,
            idOrder = state.orderData.id,
            reduceRecipe = reduceRecipe,
            listOrderRecipe = listRecipesInOrder,
            viewModelDetailHookah = viewModelDetailHookah,
            onClickCancel = onClickCancel,
//            onClickReduceRecipe = onClickReduceRecipe,
            modifier = modifier,
        )
    } else {
        LandscapeDetailUpdateOrderView(
            recipe = state.data,
            idOrder = idOrder,
//            reduceRecipe = reduceRecipe,
            listOrderRecipe = listRecipesInOrder,
            onClickCancel = onClickCancel,
//            onClickReduceRecipe = onClickReduceRecipe,
            viewModelDetailHookah = viewModelDetailHookah,
            modifier = modifier,
        )
    }

}

@Composable
fun DetailHookahScreenContentOnlyRecipe(
    state: DetailHookahState.ContentOnlyRecipe,
    viewModelDetailHookah: DetailHookahViewModel,
    onClickCancel: () -> Unit,
//    onClickReduceRecipe: (List<ReduceRecipeRequest>, List<RecipeModelEntity>) -> Unit,
    recipe: RecipeModelEntity,
//    listOrderRecipe: List<RecipeModelEntity>,
    reduceRecipe: List<ReduceRecipeRequest>,
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {

    val recipeInScreen = state.data
    val listRecipesInOrder = listOf(recipe).toMutableList()

    Log.d("MyLog", "Текущий ЗАКАЗ Create Content only====> $recipeInScreen")
    Log.d(
        "MyLog",
        "Обновленный лист заказа со всеми рецептами Create Content only ====> $listRecipesInOrder"
    )


    if (ScreenOrientation == Configuration.ORIENTATION_PORTRAIT) {
        PortraitDetailCreateOrderView(
            navController = navController,
            recipe = recipeInScreen,
            reduceRecipe = reduceRecipe,
            listOrderRecipe = listRecipesInOrder,
            viewModelDetailHookah = viewModelDetailHookah,
            onClickCancel = onClickCancel,
//            onClickReduceRecipe = onClickReduceRecipe,
            modifier = modifier,
        )
    } else {
        LandscapeDetailCreateOrderView(
            navController = navController,
            recipe = recipeInScreen,
            reduceRecipe = reduceRecipe,
            listOrderRecipe = listRecipesInOrder,
            onClickCancel = onClickCancel,
//            onClickReduceRecipe = onClickReduceRecipe,
            viewModelDetailHookah = viewModelDetailHookah,
            modifier = modifier,
        )
    }

}


@Composable
fun DetailHookahScreenError(
    state: DetailHookahState.Error,
    modifier: Modifier = Modifier,
) {
    Text(text = state.error, modifier = Modifier.then(modifier))

}


@Composable
fun DetailHookahScreenLoading(
    state: DetailHookahState.Loading,
    modifier: Modifier = Modifier,
) {

    Box(contentAlignment = Alignment.Center, modifier = Modifier.then(modifier)) {
        CircularProgressIndicator(
            modifier = Modifier
                .width(48.dp)
                .then(modifier),
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    }
}



