package com.pidzama.smokecrafthookahapp.presentation.detail_hookah

import android.content.res.Configuration
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.pidzama.smokecrafthookahapp.R
import com.pidzama.smokecrafthookahapp.data.model.RandomRecipeSubList
import com.pidzama.smokecrafthookahapp.navigation.MainScreen
import com.pidzama.smokecrafthookahapp.presentation.common.bounceClick
import com.pidzama.smokecrafthookahapp.ui.theme.ScreenOrientation
import com.pidzama.smokecrafthookahapp.ui.theme.dimens
import com.pidzama.smokecrafthookahapp.utils.Constants.TastyWeight.ListTastyWeight


@Composable
fun DetailHookahScreen(
    navController: NavHostController = rememberNavController(),
    recipe: RandomRecipeSubList,
    darkTheme: Boolean, onThemeUpdated: () -> Unit
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
                        androidx.compose.material3.Text(
                            text = "Заказы",
                            style = MaterialTheme.typography.titleLarge,
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
                PortraitDetailView(
                    navController = navController,
                    recipe = recipe,
                    darkTheme = darkTheme,
                    onThemeUpdated = onThemeUpdated
                )
            } else {
                LandscapeDetailView(
                    navController = navController,
                    recipe = recipe,
                    darkTheme = darkTheme,
                    onThemeUpdated = onThemeUpdated
                )
            }
        }
    )
}


@Composable
fun PortraitDetailView(
    navController: NavHostController,
    recipe: RandomRecipeSubList,
    darkTheme: Boolean, onThemeUpdated: () -> Unit,
    listTobaccoWeight: List<Float> = ListTastyWeight
) {
    val context = LocalContext.current
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val screenHeight = LocalConfiguration.current.screenHeightDp

    val viewModel = hiltViewModel<DetailHookahViewModel>()
    val rediceRecipe = viewModel.reduceRecipe.value
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = (screenHeight / 14).dp,
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
                    text = "Заказ №${(1..20).random()}/ стол №${(1..8).random()}",
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.inverseSurface,
                    style = MaterialTheme.typography.headlineMedium,
                )
                DetailPieChart(
                    input = recipe,
                    listTobaccoWeight = listTobaccoWeight
                )
            }
        }
        BoxWithConstraints(modifier = Modifier.weight(0.2f)) {
            Column(modifier = Modifier.padding(vertical = MaterialTheme.dimens.extraSmall)) {
                Button(
                    onClick = {
                        navController.navigate(MainScreen.CurrentOrders.route)
                        viewModel.reduceRecipe(recipe)
                        Toast.makeText(context, "${rediceRecipe?.result}", Toast.LENGTH_SHORT)
                            .show()
                    },
                    modifier = Modifier
                        .bounceClick()
                        .fillMaxWidth()
                        .height((screenWidth / 8).dp),
                    shape = RoundedCornerShape(MaterialTheme.dimens.cornerShape),
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
                ) {
                    Log.d("MyLog", "Списанный рецепт===>${viewModel.reduceRecipe.value} ")
                    Log.d("MyLog", "РЕЦЕПТ===>${recipe} ")
                    Log.d("MyLog", "Списание===>${viewModel.reduceRecipe(recipe)} ")
                    Text(
                        text = "Списать со склада",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                }
                Spacer(modifier = Modifier.height(MaterialTheme.dimens.small1))
                OutlinedButton(
                    onClick = { navController.navigate(MainScreen.CurrentOrders.route) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height((screenWidth / 8).dp),
                    shape = RoundedCornerShape(MaterialTheme.dimens.cornerShape),
                    border = BorderStroke(2.dp, color = MaterialTheme.colorScheme.primary),
                ) {
                    Text(
                        text = "Отменить",
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
    recipe: RandomRecipeSubList,
    darkTheme: Boolean, onThemeUpdated: () -> Unit,
    listTobaccoWeight: List<Float> = ListTastyWeight
) {
    val context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = MaterialTheme.dimens.medium1,
                start = MaterialTheme.dimens.small2,
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
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(MaterialTheme.dimens.small3),
                    text = "Заказ №${(1..20).random()}/ стол №${(1..8).random()}",
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.inverseSurface,
                    style = MaterialTheme.typography.titleLarge,
                )
                LandscapeDetailPieChart(
                    input = recipe,
                    listTobaccoWeight = listTobaccoWeight
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
                    onClick = { Toast.makeText(context, "try later", Toast.LENGTH_SHORT).show() },
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
                    onClick = { navController.navigate(MainScreen.CurrentOrders.route) },
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


