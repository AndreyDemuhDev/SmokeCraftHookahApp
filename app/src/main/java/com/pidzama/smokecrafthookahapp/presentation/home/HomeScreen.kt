package com.pidzama.smokecrafthookahapp.presentation.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.pidzama.smokecrafthookahapp.presentation.common.CardItemWorker


@Composable
fun HomeScreen(
    navController: NavHostController = rememberNavController()
) {

    val viewModel = hiltViewModel<HomeViewModel>()
    val listAllWorkers = viewModel.allWorkers.observeAsState(listOf()).value
    viewModel.getAllWorkers()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 60.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Text(text = "Текущие заказы")
            }
            items(listAllWorkers) { worker ->
                CardItemWorker(worker = worker)
            }
        }
    }


}
