package com.pidzama.smokecrafthookahapp.presentation.settings

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.pidzama.smokecrafthookahapp.R
import com.pidzama.smokecrafthookahapp.navigation.MainScreen
import com.pidzama.smokecrafthookahapp.presentation.home.HomeViewModel
import com.pidzama.smokecrafthookahapp.presentation.random_generation_recipe.CustomData
import com.pidzama.smokecrafthookahapp.presentation.random_generation_recipe.common.MyPieDiagramm
import com.pidzama.smokecrafthookahapp.ui.theme.dimens


@Composable
fun SettingsScreen(
    navController: NavHostController,
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
                            text = "Рецепты",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    }
                },
                backgroundColor = MaterialTheme.colorScheme.primary,
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(MainScreen.Home.route) }) {
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
            CustomCustiomData(navController = navController)
        }
    )
}

@Composable
fun CustomCustiomData(navController: NavHostController) {
    val viewModel = hiltViewModel<HomeViewModel>()
    val listRandomGenerateRecipe = viewModel.generateRecipeList.observeAsState(listOf()).value

    LaunchedEffect(key1 = Unit) {
        viewModel.getListRandomGenerateRecipe()
    }

    Log.d("MyLog", "CUSTOMDATA listRandomGenerateRecipe $listRandomGenerateRecipe")
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
                items(listRandomGenerateRecipe) { list ->
                    PIEPIECHART(input = list, navController = navController)
                }
            }
        }
        Box(modifier = Modifier.weight(0.1f)) {
            androidx.compose.material3.Button(
                onClick = { viewModel.getListRandomGenerateRecipe() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(MaterialTheme.dimens.large2),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
            ) {
                androidx.compose.material3.Text(
                    text = "Сгенерировать новый рецепт",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}





