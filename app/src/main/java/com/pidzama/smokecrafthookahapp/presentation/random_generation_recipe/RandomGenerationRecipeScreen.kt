package com.pidzama.smokecrafthookahapp.presentation.random_generation_recipe

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.pidzama.smokecrafthookahapp.R
import com.pidzama.smokecrafthookahapp.data.model.generate_model.ModelRecipeItem
import com.pidzama.smokecrafthookahapp.presentation.common.RecipeItemCard
import com.pidzama.smokecrafthookahapp.presentation.common.TopBarContent
import com.pidzama.smokecrafthookahapp.presentation.common.bounceClick
import com.pidzama.smokecrafthookahapp.presentation.recipe_generation_method.RecipeGenerationViewModel
import com.pidzama.smokecrafthookahapp.ui.theme.ScreenOrientation
import com.pidzama.smokecrafthookahapp.ui.theme.dimens


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RandomGenerationRecipeScreen(
    navController: NavHostController = rememberNavController(),
    navigateToDetails: (ModelRecipeItem, Int) -> Unit,
    viewModel: RecipeGenerationViewModel
) {

    val isUseSwipe by viewModel.swipeIsLoading.collectAsState()
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isUseSwipe)

    Scaffold(
        topBar = {
            TopBarContent(
                navController = navController,
                canNavigateBack = true,
                title = R.string.recipes
            )
        },
        content = {
            SwipeRefresh(state = swipeRefreshState, onRefresh = viewModel::useSwipe) {
                if (ScreenOrientation == Configuration.ORIENTATION_PORTRAIT) {
                    PortraitRecipesContentView(
                        navigateToDetails = navigateToDetails,
                        viewModel = viewModel
                    )
                } else {
                    LandscapeRecipesContentView(
                        navigateToDetails = navigateToDetails,
                        viewModel = viewModel
                    )
                }
            }
        }
    )
}

@Composable
fun PortraitRecipesContentView(
    navigateToDetails: (ModelRecipeItem, Int) -> Unit,
    viewModel: RecipeGenerationViewModel,
) {

    val result = viewModel.res.value
    if (result.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
    if (result.error.isNotEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = result.error,
                style = MaterialTheme.typography.titleLarge
            )
            Log.d("MyLog", "error = ${result.error}")
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.small2))
            Image(
                painter = painterResource(id = R.drawable.ic_error),
                contentDescription = "error_logo",
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
            )
        }
    }
    if (result.data.isNotEmpty()) {
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
                    items(items = result.data) { recipe ->
                        Card(
                            modifier = Modifier,
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
                        ) {
                            RecipeItemCard(
                                input = recipe,
                                onClickToDetailsScreen = {
                                    navigateToDetails(
                                        recipe,
                                        1
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .heightIn(min = 190.dp)
                            )
                        }
                    }
                }
            }
            Box(modifier = Modifier.weight(0.1f)) {
                Button(
                    onClick = {
                        viewModel.getListRecipes()
                    },
                    modifier = Modifier
                        .bounceClick()
                        .fillMaxWidth()
                        .height(MaterialTheme.dimens.buttonHeight),
                    shape = RoundedCornerShape(MaterialTheme.dimens.cornerShape),
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
                ) {
                    Text(
                        text = stringResource(id = R.string.generate_new_recipe),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }
    }
}


@Composable
fun LandscapeRecipesContentView(
    navigateToDetails: (ModelRecipeItem, Int) -> Unit,
    viewModel: RecipeGenerationViewModel,
) {

    val screenHeight = LocalConfiguration.current.screenHeightDp
    val result = viewModel.res.value

    if (result.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
    if (result.error.isNotEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.error_connection),
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.small2))
            Image(
                painter = painterResource(id = R.drawable.ic_error),
                contentDescription = "error_logo",
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
            )
        }
    }
    if (result.data.isNotEmpty()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = (screenHeight / 6).dp,
                    start = (screenHeight / 20).dp,
                    end = (screenHeight / 20).dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.weight(0.9f)) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    items(items = result.data) { recipe ->
                        Card(
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
                        ) {
                            RecipeItemCard(
                                input = recipe,
                                onClickToDetailsScreen = {
                                    navigateToDetails(
                                        recipe,
                                        1
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .heightIn(min = 190.dp)
                            )
                        }
                    }
                }
            }
            Box(modifier = Modifier.weight(0.2f)) {
                Button(
                    onClick = {
                        viewModel.getListRecipes()
                    },
                    modifier = Modifier
                        .bounceClick()
                        .fillMaxWidth()
                        .height(MaterialTheme.dimens.buttonHeight)
                        .padding(horizontal = (screenHeight / 25).dp),
                    shape = RoundedCornerShape(MaterialTheme.dimens.cornerShape),
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_refresh),
                        contentDescription = "refresh_icon"
                    )
                }
            }
        }
    }
}
