package com.pidzama.smokecrafthookahapp.presentation.detail_hookah

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.pidzama.smokecrafthookahapp.R
import com.pidzama.smokecrafthookahapp.data.model.RandomRecipeSubList
import com.pidzama.smokecrafthookahapp.navigation.MainScreen
import com.pidzama.smokecrafthookahapp.ui.theme.dimens


@Composable
fun DetailHookahScreen(navController: NavHostController = rememberNavController(), recipe: RandomRecipeSubList?) {

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
            OrderInfo(navController = navController, recipe=recipe)
        }
    )
}

@Composable
fun OrderInfo(navController: NavHostController, recipe: RandomRecipeSubList?) {

    val context = LocalContext.current
    val screenWidth = LocalConfiguration.current.screenWidthDp

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = (screenWidth / 20).dp)
    ) {
        Log.d("MyLog", "${(screenWidth /20).dp}")
        Column(
            modifier = Modifier
                .padding(top = MaterialTheme.dimens.large2)
                .weight(weight = 0.8f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(modifier = Modifier.padding(MaterialTheme.dimens.medium1),
                text = "Заказ №12/ стол №3",
                color = MaterialTheme.colorScheme.inverseSurface,
                style = MaterialTheme.typography.titleLarge,
            )
            DetailPieChart(input = recipe!!, navController = navController)
        }

        Box(modifier = Modifier.weight(0.2f)) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = { Toast.makeText(context, "try later", Toast.LENGTH_SHORT).show() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = MaterialTheme.dimens.small1)
                        .height(MaterialTheme.dimens.large2),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
                ) {
                    Text(
                        text = "Списать со склада",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                }
                OutlinedButton(
                    onClick = { navController.navigate(MainScreen.CurrentOrders.route) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = MaterialTheme.dimens.small1)
                        .height(MaterialTheme.dimens.large2),
                    shape = RoundedCornerShape(10.dp),
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


