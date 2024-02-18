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
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.pidzama.smokecrafthookahapp.R
import com.pidzama.smokecrafthookahapp.presentation.archive_orders.common.TastyBarChart
import com.pidzama.smokecrafthookahapp.presentation.detail_hookah.DetailHookahViewModel
import com.pidzama.smokecrafthookahapp.ui.theme.dimens


@Composable
fun OrderArchiveScreen(
    viewModel: DetailHookahViewModel = hiltViewModel(),
    viewModelCurrent: ArchiveViewModel = hiltViewModel()
) {
    val listOrdersRecipe = viewModel.listArchiveTobaccos.collectAsState()

    val listAllTobaccos = viewModelCurrent.listAllTobaccos.value.data

    val groupListOrdersTobaccos = listOrdersRecipe.value.groupingBy { it.taste_group }.eachCount()
        .entries
        .sortedByDescending { it.value }

    val groupListAllTobaccos = listAllTobaccos.groupingBy { it.taste_group }.eachCount()
        .entries
        .sortedByDescending { it.value }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState()),
        color = MaterialTheme.colorScheme.background,
        content = {

            if (listOrdersRecipe.value.isNotEmpty() && listAllTobaccos.isNotEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            vertical = MaterialTheme.dimens.small3,
                            horizontal = MaterialTheme.dimens.small1
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.quantity_of_tobacco_ordered),
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.inverseSurface
                    )
                    TastyBarChart(groupListOrdersTobaccos)
                    Text(
                        text = stringResource(id = R.string.list_of_all_tobacco),
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.inverseSurface
                    )
                    TastyBarChart(groupListAllTobaccos)
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
                        Text(
                            text = stringResource(id = R.string.empty_list_order),
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.inverseSurface
                        )
                    }

                }
            }
        })
}


