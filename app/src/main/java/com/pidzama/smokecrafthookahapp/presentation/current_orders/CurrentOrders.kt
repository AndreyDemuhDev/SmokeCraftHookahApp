package com.pidzama.smokecrafthookahapp.presentation.current_orders

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.pidzama.smokecrafthookahapp.navigation.MainScreen
import com.pidzama.smokecrafthookahapp.ui.theme.dimens


@Composable
fun CurrentOrders(
    navController: NavHostController = rememberNavController()
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
        content = {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
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
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onBackground,
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
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onBackground,
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
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onBackground,
                        )
                    }
                }
            }
        }
    )
}
