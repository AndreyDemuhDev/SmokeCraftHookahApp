package com.pidzama.smokecrafthookahapp.presentation.choose_generate_recipe

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.pidzama.smokecrafthookahapp.navigation.MainScreen
import com.pidzama.smokecrafthookahapp.ui.theme.dimens

@Composable
fun CreateRecipeScreen(
    navController: NavHostController = rememberNavController()
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
        content = {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Center) {
                Column(
                    modifier = Modifier.padding(horizontal = (screenWidth / 8).dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    Button(
                        onClick = { navController.navigate(MainScreen.ChooseGenerateRecipe.route) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(MaterialTheme.dimens.large2),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text = "Случайная генерация",
                            fontSize = 17.sp,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onBackground,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Button(
                        onClick = {/*TODO*/ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(MaterialTheme.dimens.large2),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text = "Фирменные миксы",
                            fontSize = 17.sp,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onBackground,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Button(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(MaterialTheme.dimens.large2),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text = "Генерация по ингредиенту",
                            fontSize = 17.sp,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onBackground,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    )
}


@Preview
@Composable
fun Preview() {
    CreateRecipeScreen()
}