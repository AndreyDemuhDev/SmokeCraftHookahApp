package com.pidzama.smokecrafthookahapp.presentation.recipe_generation_method

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.pidzama.smokecrafthookahapp.R
import com.pidzama.smokecrafthookahapp.navigation.MainScreen
import com.pidzama.smokecrafthookahapp.presentation.common.NoRippleEffect
import com.pidzama.smokecrafthookahapp.presentation.common.bounceClick
import com.pidzama.smokecrafthookahapp.ui.theme.ScreenOrientation
import com.pidzama.smokecrafthookahapp.ui.theme.dimens
import com.talhafaki.composablesweettoast.util.SweetToastUtil


@Composable
fun RecipeGenerationMethodScreen(
    navController: NavHostController = rememberNavController(),
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState()),
        color = MaterialTheme.colorScheme.background,
        content = {
            if (ScreenOrientation == Configuration.ORIENTATION_PORTRAIT) {
                PortraitCurrentOrderScreen(
                    navController = navController,
                    screenWidth = screenWidth,
                )
            } else {
                LandscapeCurrentOrderScreen(
                    navController = navController,
                    screenWidth = screenWidth,
                )
            }
        }
    )
}

@Composable
fun PortraitCurrentOrderScreen(
    navController: NavHostController,
    screenWidth: Int = LocalConfiguration.current.screenWidthDp,
) {

    var openToastInfo by remember { mutableStateOf(false) }
    if (openToastInfo) {
        openToastInfo = false
        SweetToastUtil.SweetInfo(
            message = stringResource(id = R.string.not_available),
            duration = Toast.LENGTH_SHORT,
            padding = PaddingValues(top = MaterialTheme.dimens.medium1),
            contentAlignment = Alignment.TopCenter
        )
    }

    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier.padding(horizontal = (screenWidth / 8).dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.medium1)
        ) {
            Button(
                onClick = {
//                    openToastInfo = true
                    navController.navigate(MainScreen.ChooseGenerateRecipe.route)
                          },
                modifier = Modifier
                    .bounceClick()
                    .fillMaxWidth()
                    .height(MaterialTheme.dimens.buttonHeight),
                shape = RoundedCornerShape(MaterialTheme.dimens.cornerShape),
            ) {
                Text(
                    text = stringResource(id = R.string.random_generation),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }
            Button(
                onClick = { openToastInfo = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(MaterialTheme.dimens.buttonHeight),
                shape = RoundedCornerShape(MaterialTheme.dimens.cornerShape),
                interactionSource = remember { NoRippleEffect() }
            ) {
                Text(
                    text = stringResource(id = R.string.branded_mixes),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }
            Button(
                onClick = { openToastInfo = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(MaterialTheme.dimens.buttonHeight),
                shape = RoundedCornerShape(MaterialTheme.dimens.cornerShape),
                interactionSource = remember { NoRippleEffect() }
            ) {
                Text(
                    text = stringResource(id = R.string.generation_by_ingredient),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }
        }
    }
}

@Composable
fun LandscapeCurrentOrderScreen(
    navController: NavHostController,
    screenWidth: Int = LocalConfiguration.current.screenWidthDp
) {
    var openToastInfo by remember { mutableStateOf(false) }
    if (openToastInfo) {
        openToastInfo = false
        SweetToastUtil.SweetInfo(
            message = stringResource(id = R.string.not_available),
            duration = Toast.LENGTH_SHORT,
            padding = PaddingValues(top = MaterialTheme.dimens.medium1),
            contentAlignment = Alignment.TopCenter
        )
    }

    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = (screenWidth / 4).dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.small3)
        ) {
            Button(
                onClick = { navController.navigate(MainScreen.ChooseGenerateRecipe.route) },
                modifier = Modifier
                    .bounceClick()
                    .fillMaxWidth()
                    .height(MaterialTheme.dimens.buttonHeight),
                shape = RoundedCornerShape(MaterialTheme.dimens.cornerShape),
            ) {
                Text(
                    text = stringResource(id = R.string.random_generation),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }
            Button(
                onClick = { openToastInfo = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(MaterialTheme.dimens.buttonHeight),
                shape = RoundedCornerShape(MaterialTheme.dimens.cornerShape),
                interactionSource = remember { NoRippleEffect() }
            ) {
                Text(
                    text = stringResource(id = R.string.branded_mixes),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }
            Button(
                onClick = { openToastInfo = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(MaterialTheme.dimens.buttonHeight),
                shape = RoundedCornerShape(MaterialTheme.dimens.cornerShape),
                interactionSource = remember { NoRippleEffect() }
            ) {
                Text(
                    text = stringResource(id = R.string.generation_by_ingredient),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.large1 / 3))
        }
    }
}
