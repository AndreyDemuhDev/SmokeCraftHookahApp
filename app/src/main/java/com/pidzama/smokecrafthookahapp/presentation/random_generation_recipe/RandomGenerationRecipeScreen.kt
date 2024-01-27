package com.pidzama.smokecrafthookahapp.presentation.random_generation_recipe

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.pidzama.smokecrafthookahapp.R
import com.pidzama.smokecrafthookahapp.navigation.MainScreen
import com.pidzama.smokecrafthookahapp.presentation.home.HomeViewModel
import com.pidzama.smokecrafthookahapp.presentation.random_generation_recipe.common.MyPieDiagramm
import com.pidzama.smokecrafthookahapp.ui.theme.dimens
import kotlinx.coroutines.launch

@Composable
fun RandomGenerationRecipeScreen(
    navController: NavHostController = rememberNavController()
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
            CustomData(navController = navController)
        }
    )
}

@Composable
fun CustomData(navController: NavHostController) {
    val viewModel = hiltViewModel<HomeViewModel>()
    val listRandomGenerateRecipe = viewModel.generateRecipeList.observeAsState(listOf()).value

    var indexRecipe = 0
    LaunchedEffect(key1 = Unit) {
        viewModel.getListRandomGenerateRecipe()
    }

    Log.d("MyLog", "CUSTOMDATA listRandomGenerateRecipe $listRandomGenerateRecipe")
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = MaterialTheme.dimens.large2,
                start = MaterialTheme.dimens.small3,
                end = MaterialTheme.dimens.small3
            )
    ) {
        Box(modifier = Modifier.weight(1.2f)) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(listRandomGenerateRecipe) { list ->
                    indexRecipe++
                    MyPieDiagramm(
                        input = list,
                        navController = navController,
                        indexRecipe = indexRecipe
                    )
                }
            }
        }
        Box(modifier = Modifier.weight(0.1f)) {
            Button(
                onClick = {
                    viewModel.getListRandomGenerateRecipe()

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(MaterialTheme.dimens.large2),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
            ) {
                Text(
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
