package com.pidzama.smokecrafthookahapp.presentation.detail_hookah

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.pidzama.smokecrafthookahapp.R
import com.pidzama.smokecrafthookahapp.data.model.Recipe
import com.pidzama.smokecrafthookahapp.data.model.Tabacco
import com.pidzama.smokecrafthookahapp.navigation.MainScreen
import com.pidzama.smokecrafthookahapp.ui.theme.dimens


val recipe = Recipe(
    id = 4,
    tabaccos = listOf(
        Tabacco(
            name = "Ice Mint",
            creater = "Minimal",
            value = 12,
            color = Color(0xFF65FCCF)
        ),
        Tabacco(
            name = "Melon",
            creater = "Palladium",
            value = 6,
            color = Color(0xFFF9882B)
        ),
        Tabacco(
            name = "Lemon",
            creater = "Jibiar",
            value = 2,
            color = Color(0xFFF4EA00)
        )
    )
)

@Composable
fun DetailHookahScreen(navController: NavHostController = rememberNavController()) {

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
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    }
                },
                backgroundColor = MaterialTheme.colorScheme.primary,
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(MainScreen.ChooseGenerateRecipe.route) }) {
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
            OrderInfo(navController = navController)
        }
    )
}

@Composable
fun OrderInfo(navController: NavHostController) {

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
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            )
            DetailPieChart(input = recipe, navController = navController)
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
                        fontSize = 17.sp,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold
                    )
                }
                OutlinedButton(
                    onClick = { navController.navigate(MainScreen.ChooseGenerateRecipe.route) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = MaterialTheme.dimens.small1)
                        .height(MaterialTheme.dimens.large2),
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(2.dp, color = MaterialTheme.colorScheme.primary),
                ) {
                    Text(
                        text = "Отменить",
                        fontSize = 17.sp,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}


