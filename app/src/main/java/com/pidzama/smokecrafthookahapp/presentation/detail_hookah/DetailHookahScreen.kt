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
) {
    viewModelDetailHookah.getInfoHookah(recipe, idOrder)

    val reduceRecipe = listOf<ReduceRecipeRequest>().toMutableList()

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
                viewModelDetailHookah = viewModelDetailHookah,
                state = state,
                reduceRecipe = reduceRecipe,
                onClickCancel = {
                    navController.popBackStack(MainScreen.CurrentOrders.route, false)
                },
                onClickCreateOrder = {
                    navController.navigate(MainScreen.CurrentOrders.route) {
                        popUpTo(Graph.MAIN)
                        launchSingleTop = true
                    }
                },
                onClickUpdateOrder = {
                    navController.navigate(MainScreen.CurrentOrders.route) {
                        popUpTo(Graph.MAIN)
                        launchSingleTop = true
                    }
                },
                idOrder = idOrder,
                modifier = Modifier.padding(innerPadding)
            )
        }
    )
}


@Composable
fun PortraitDetailUpdateOrderView(
    recipe: RecipeModelEntity,
    tableNumber: Int,
    idOrder: Int,
    onClickCancel: () -> Unit,
    onClickUpdateRecipe: () -> Unit,
    modifier: Modifier = Modifier,
) {

    val screenWidth = LocalConfiguration.current.screenWidthDp
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
                        onClickUpdateRecipe()
                    },
                    modifier = Modifier
                        .bounceClick()
                        .fillMaxWidth()
                        .height((screenWidth / 8).dp),
                    shape = RoundedCornerShape(MaterialTheme.dimens.cornerShape),
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
                ) {
                    Text(
                        text = stringResource(id = R.string.add_to_order),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                }
                Spacer(modifier = Modifier.height(MaterialTheme.dimens.small1))
                OutlinedButton(
                    onClick = { onClickCancel() },
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
    recipe: RecipeModelEntity,
//    listOrderRecipe: List<RecipeModelEntity>,
    tableNumber: String,
    onChangeTableNumber: (String) -> Unit,
    onClickCancel: () -> Unit,
    onClickCreateOrder: () -> Unit,
    modifier: Modifier = Modifier,
) {

    val screenWidth = LocalConfiguration.current.screenWidthDp
//    val screenHeight = LocalConfiguration.current.screenHeightDp
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
                    value = tableNumber,
                    onValueChange = onChangeTableNumber,
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
                        onClickCreateOrder()
                    },
                    enabled = tableNumber.isNotEmpty(),
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
                    onClick = { onClickCancel() },
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
    recipe: RecipeModelEntity,
    tableNumber: Int,
    idOrder: Int,
    onClickCancel: () -> Unit,
    onClickUpdateRecipe: () -> Unit,
    modifier: Modifier = Modifier,
) {

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
                        openDialogSuccess = true
                        onClickUpdateRecipe()
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
                    onClick = { onClickCancel() },
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
    recipe: RecipeModelEntity,
    tableNumber: String,
    onChangeTableNumber: (String) -> Unit,
    onClickCancel: () -> Unit,
    onClickCreateOrder: () -> Unit,
    modifier: Modifier = Modifier,
) {

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
                    value = tableNumber,
                    onValueChange = onChangeTableNumber,
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
                DetailsRecipeItemCard(
                    input = recipe,
                    isLandscape = true
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
                        openDialogSuccess = true
                        onClickCreateOrder()
                    },
                    enabled = tableNumber.isNotEmpty(),
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
                    onClick = { onClickCancel() },
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
    onClickCreateOrder: () -> Unit,
    onClickUpdateOrder: () -> Unit,
    onClickCancel: () -> Unit,
    reduceRecipe: List<ReduceRecipeRequest>,
    idOrder: Int,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    val screenModifier = Modifier
        .fillMaxSize()
        .padding(contentPadding)
        .then(modifier)
    when (state) {
        is DetailHookahState.ContentRecipeWithOrder -> {
            val recipeInScreen = state.data         //рецепт
            val listRecipesInOrder =
                listOf(recipeInScreen).toMutableList() //список рецептов в заказе который отправляем на сервак
            val currentOrder =
                state.orderData.recipes.toMutableList()      //текущий рецепт на экране
            val hookahCount = state.orderData.recipes.size + 1       //количество кальянов в заказе
            val tableNumber = state.orderData.table_number         //номер стола
            listRecipesInOrder.addAll(currentOrder)
            Log.d("MyLog", "Рецепт => $recipeInScreen")
            Log.d("MyLog", "Список заказанных рецептов (WITH ORDER) => $listRecipesInOrder")
            Log.d("MyLog", "Номер Заказа $idOrder")
            Log.d("MyLog", "Количество кальянов $hookahCount")
            Log.d("MyLog", "Номер столика $tableNumber")

            DetailHookahScreenContentRecipeWithOrder(
                state = state,
                recipe = recipeInScreen,
                onClickUpdateRecipe = {
                    viewModelDetailHookah.reduceRecipe(reduceRecipe)
                    viewModelDetailHookah.updateOrder(
                        id = idOrder,
                        order = OrderRequest(
                            is_active = true,
                            table_number = tableNumber,
                            hookah_count = hookahCount,
                            recipes = listRecipesInOrder
                        )
                    )
                    onClickUpdateOrder()
                },
                tableNumber = tableNumber,
                onClickCancel = onClickCancel,
                modifier = screenModifier,
            )
        }

        is DetailHookahState.ContentOnlyRecipe -> {
            val recipeInScreen = state.data                 //рецепт который создаем
            val listRecipesInOrder =
                listOf(recipeInScreen).toMutableList() //рецепт отправляем на сервер
            var tableNumber by rememberSaveable {
                mutableStateOf("")
            }

            Log.d("MyLog", "Рецепт => $recipeInScreen")
            Log.d("MyLog", "Список заказанных рецептов (ONLY RECIPE) => $listRecipesInOrder")
            Log.d("MyLog", "Номер Заказа $idOrder")

            DetailHookahScreenContentOnlyRecipe(
                recipe = recipeInScreen,
                onClickCreateOrder = {
                    viewModelDetailHookah.reduceRecipe(recipe = reduceRecipe)
                    viewModelDetailHookah.createOrder(
                        order = OrderRequest(
                            is_active = true,
                            table_number = tableNumber.toInt(),
                            hookah_count = 1,
                            recipes = listRecipesInOrder
                        )
                    )
                    onClickCreateOrder()
                },
                tableNumber = tableNumber,
                onChangeTableNumber = { tableNumber = it },
                onClickCancel = onClickCancel,
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
    onClickCancel: () -> Unit,
    onClickUpdateRecipe: () -> Unit,
    tableNumber: Int,
    recipe: RecipeModelEntity,
    modifier: Modifier = Modifier,
) {
    val idOrder = state.orderData.id

    if (ScreenOrientation == Configuration.ORIENTATION_PORTRAIT) {
        PortraitDetailUpdateOrderView(
            recipe = recipe,
            tableNumber = tableNumber,
            onClickUpdateRecipe = onClickUpdateRecipe,
            idOrder = idOrder,
            onClickCancel = onClickCancel,
            modifier = modifier,
        )
    } else {
        LandscapeDetailUpdateOrderView(
            recipe = recipe,
            tableNumber = tableNumber,
            onClickUpdateRecipe = onClickUpdateRecipe,
            idOrder = idOrder,
            onClickCancel = onClickCancel,
            modifier = modifier,
        )
    }

}

@Composable
fun DetailHookahScreenContentOnlyRecipe(
    recipe: RecipeModelEntity,
    onClickCancel: () -> Unit,
    onClickCreateOrder: () -> Unit,
    tableNumber: String,
    onChangeTableNumber: (String) -> Unit,
    modifier: Modifier = Modifier,
) {

    if (ScreenOrientation == Configuration.ORIENTATION_PORTRAIT) {
        PortraitDetailCreateOrderView(
            tableNumber = tableNumber,
            onChangeTableNumber = onChangeTableNumber,
            recipe = recipe,
            onClickCreateOrder = onClickCreateOrder,
            onClickCancel = onClickCancel,
            modifier = modifier,
        )
    } else {
        LandscapeDetailCreateOrderView(
            tableNumber = tableNumber,
            onChangeTableNumber = onChangeTableNumber,
            recipe = recipe,
            onClickCreateOrder = onClickCreateOrder,
            onClickCancel = onClickCancel,
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



