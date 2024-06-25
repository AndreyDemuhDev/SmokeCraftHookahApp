package com.pidzama.smokecrafthookahapp.presentation.detail_order

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.pidzama.smokecrafthookahapp.R
import com.pidzama.smokecrafthookahapp.presentation.common.CloseOrderAlertDialog
import com.pidzama.smokecrafthookahapp.presentation.common.ExtendedFloatingActionCustomButton
import com.pidzama.smokecrafthookahapp.presentation.common.OrderItemCard
import com.pidzama.smokecrafthookahapp.presentation.common.TopBarContent


@Composable
fun DetailOrderScreen(
    id: Int,
    viewModel: DetailOrderViewModel,
    navController: NavHostController,
    onClickCloseOrder: () -> Unit,
    onClickAddRecipe: (Int) -> Unit,
    modifier: Modifier = Modifier
) {

    val state by viewModel.detailOrderState.collectAsState()

    val openAlertDialog: MutableState<Boolean> = rememberSaveable() { mutableStateOf(false) }

    CloseOrderAlertDialog(
        openAlertDialog,
        action = {
            viewModel.deleteOrder(id)
            onClickCloseOrder()
        }
    )

    LaunchedEffect(key1 = Unit) {
        viewModel.getInfoOrder(id)
    }

    Scaffold(
        topBar = {
            TopBarContent(
                onClickBack = {navController.popBackStack()},
                canNavigateBack = true,
                title = R.string.order
            )
        },
        floatingActionButton = {
            Column(
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.End
            ) {
                ExtendedFloatingActionCustomButton(
                    icon = R.drawable.ic_close,
                    contentDescription = R.string.close,
                    onClick = { openAlertDialog.value = true },
                    textColor = MaterialTheme.colorScheme.inverseSurface,
                    backgroundColor = MaterialTheme.colorScheme.error
                )
                ExtendedFloatingActionCustomButton(
                    icon = R.drawable.add_plus,
                    contentDescription = R.string.add,
                    onClick = { onClickAddRecipe(id) },
                    textColor = MaterialTheme.colorScheme.inverseSurface,
                    backgroundColor = MaterialTheme.colorScheme.primary
                )
            }
        },
        modifier = modifier
            .statusBarsPadding()
            .navigationBarsPadding()
    ) { innerPadding ->
        DetailOrderState(
            state = state,
            modifier = Modifier.padding(innerPadding)
        )

    }
}

@Composable
fun DetailOrderState(
    state: DetailOrderState,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {

    val screenModifier = Modifier
        .fillMaxSize()
        .padding(contentPadding)
        .then(modifier)
    when (state) {
        is DetailOrderState.Content -> {
            DetailOrderContent(
                state = state,
                modifier = screenModifier
            )
        }

        is DetailOrderState.Error -> {
            DetailOrderError(state = state, modifier = screenModifier)
        }

        is DetailOrderState.Loading -> {
            DetailOrderLoading(state = state, modifier = screenModifier)
        }
    }

}

@Composable
fun DetailOrderContent(
    state: DetailOrderState.Content,
    modifier: Modifier = Modifier
) {

    Column(modifier = modifier.background(MaterialTheme.colorScheme.background)) {
        Text(
            text = "Стол №${state.data.table_number}",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.inverseSurface,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
        )
        LazyColumn {
            items(state.data.recipes) { item ->
                OrderItemCard(
                    input = item,
                    onClickToDetailsScreen = { },
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 190.dp)
                        .padding(vertical = 13.dp, horizontal = 16.dp)
                )
            }
        }

    }
}


@Composable
fun DetailOrderError(
    state: DetailOrderState.Error,
    modifier: Modifier = Modifier
) {
    Text(text = state.error, modifier = Modifier.then(modifier))
}

@Composable
fun DetailOrderLoading(
    state: DetailOrderState.Loading,
    modifier: Modifier = Modifier
) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.then(modifier)) {
        CircularProgressIndicator(
            modifier = Modifier
                .width(48.dp)
                .then(modifier),
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    }
}