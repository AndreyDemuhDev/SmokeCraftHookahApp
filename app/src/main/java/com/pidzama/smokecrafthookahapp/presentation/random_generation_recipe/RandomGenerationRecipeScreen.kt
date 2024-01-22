package com.pidzama.smokecrafthookahapp.presentation.random_generation_recipe

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.pidzama.smokecrafthookahapp.R
import com.pidzama.smokecrafthookahapp.data.model.Recipe
import com.pidzama.smokecrafthookahapp.data.model.Tabacco
import com.pidzama.smokecrafthookahapp.navigation.MainScreen
import com.pidzama.smokecrafthookahapp.presentation.home.HomeViewModel
import com.pidzama.smokecrafthookahapp.presentation.random_generation_recipe.common.MyPieDiagramm
import com.pidzama.smokecrafthookahapp.ui.theme.dimens

@Composable
fun RandomGenerationRecipeScreen(
    navController: NavHostController = rememberNavController()
) {

    val viewModel = hiltViewModel<HomeViewModel>()
    val listRandomGenerateRecipe = viewModel.generateRecipeList.observeAsState(listOf()).value
    viewModel.getListRandomGenerateRecipe()

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
            CustomData(data = input, navController = navController)
        }
    )
}

val input = listOf(
    Recipe(
        id = 1, listOf(
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
    ),
    Recipe(
        id = 2, listOf(
            Tabacco(
                name = "Яблоко",
                creater = "Jibba",
                value = 8,
                color = Color(0xFF009688)
            ),
            Tabacco(
                name = "Вишня",
                creater = "Mellow",
                value = 4,
                color = Color(0xFFE91E63)
            ),
            Tabacco(
                name = "Сметана",
                creater = "Ice Mint",
                value = 8,
                color = Color(0xFF3F51B5)
            )

        )
    ),
    Recipe(
        id = 3, listOf(
            Tabacco(
                name = "Баклажан",
                creater = "Jibba",
                value = 5,
                color = Color(0xFF9C27B0)
            ),
            Tabacco(
                name = "Банан",
                creater = "Mellow",
                value = 7,
                color = Color(0xFFFFEB3B)
            ),
            Tabacco(
                name = "Огурец",
                creater = "Ice Mint",
                value = 14,
                color = Color(0xFF4CAF50)
            )

        )
    )
)


@Composable
fun CustomData(data: List<Recipe>, navController: NavHostController) {
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
                item {
                    MyPieDiagramm(input = data, navController = navController)
                }
            }
        }
        Box(modifier = Modifier.weight(0.1f)) {
            Button(
                onClick = { /*TODO*/ },
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
