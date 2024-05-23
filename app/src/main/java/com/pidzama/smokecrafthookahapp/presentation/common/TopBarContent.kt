package com.pidzama.smokecrafthookahapp.presentation.common

import androidx.annotation.StringRes
import androidx.compose.material.IconButton
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.pidzama.smokecrafthookahapp.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarContent(
    navController: NavHostController,
    canNavigateBack: Boolean,
    @StringRes title: Int,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(id = title),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
        },
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.arrow_left),
                        contentDescription = "arrow_left"
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
        modifier = modifier
    )
}
