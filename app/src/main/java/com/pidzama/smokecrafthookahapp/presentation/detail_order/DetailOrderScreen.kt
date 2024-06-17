package com.pidzama.smokecrafthookahapp.presentation.detail_order

import android.util.Log
import androidx.compose.foundation.background
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
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.pidzama.smokecrafthookahapp.R
import com.pidzama.smokecrafthookahapp.presentation.common.OrderItemCard
import com.pidzama.smokecrafthookahapp.presentation.common.TopBarContent
import com.pidzama.smokecrafthookahapp.presentation.main.BottomBarApp


@Composable
fun DetailOrderScreen(
    id: Int,
    viewModel: DetailOrderViewModel,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {

    val state by viewModel.detailOrderState.collectAsState()

    LaunchedEffect(key1 = Unit) {
        Log.d("MyLog", "ViewModel.getInfoOrder $id (тут id) ${viewModel.getInfoOrder(id)}")
        viewModel.getInfoOrder(id)
    }

    Scaffold(
        topBar = {
            TopBarContent(
                navController = navController,
                canNavigateBack = true,
                title = R.string.order
            )
        },
        bottomBar = { BottomBarApp(navController = navController) },
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
                contentPadding = contentPadding,
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
    contentPadding: PaddingValues,
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
        OrderItemCard(
            input = state.data,
            title = R.string.hookah,
            onClickToDetailsScreen = { },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 190.dp)
                .padding(vertical = 13.dp, horizontal = 16.dp)
        )
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