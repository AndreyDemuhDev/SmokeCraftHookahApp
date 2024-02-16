package com.pidzama.smokecrafthookahapp.presentation.archive_orders

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.pidzama.smokecrafthookahapp.R


import com.pidzama.smokecrafthookahapp.presentation.archive_orders.common.YBarChart
import com.pidzama.smokecrafthookahapp.presentation.detail_hookah.DetailHookahViewModel
import com.pidzama.smokecrafthookahapp.ui.theme.dimens


@Composable
fun OrderArchiveScreen(
    viewModel: DetailHookahViewModel = hiltViewModel()
) {
    val listRecipes = viewModel.listArchiveTobaccos.collectAsState()

    val solution = listRecipes.value.groupingBy { it.taste_group }.eachCount()
        .entries
        .sortedByDescending { it.value }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState()),
        color = MaterialTheme.colorScheme.background,
        content = {

            if (listRecipes.value.isNotEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            vertical = MaterialTheme.dimens.small3,
                            horizontal = MaterialTheme.dimens.small1
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Количество списанного табака")
                    YBarChart(solution)
                }

            } else {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(
                            painter = painterResource(
                                id = R.drawable.empty_storage
                            ),
                            contentDescription = "empty_storage_img"
                        )
                        Spacer(modifier = Modifier.height(MaterialTheme.dimens.small3))
                        Text(text = "Заказов еще не было")
                    }

                }
            }
        })
}


