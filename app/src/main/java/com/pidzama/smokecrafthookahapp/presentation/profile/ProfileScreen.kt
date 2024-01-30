package com.pidzama.smokecrafthookahapp.presentation.profile

import android.graphics.Color
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay

@Composable
fun ProfileScreen(navController: NavHostController) {
    Text(text = "Profile Screen", color = MaterialTheme.colors.primary)

    MyComposable()
}

@Composable
fun MyComposable() {
    val isLoading = remember { mutableStateOf(false) }
    val data = remember { mutableStateOf(listOf<String>()) }

    // Define a LaunchedEffect to perform a long-running operation asynchronously
    // `LaunchedEffect` will cancel and re-launch if
    // `isLoading.value` changes
    LaunchedEffect(isLoading.value) {
        if (isLoading.value) {
            // Perform a long-running operation, such as fetching data from a network
            val newData = fetchData()
            // Update the state with the new data
            data.value = newData
            isLoading.value = false
        }
    }

    Column {
        Button(onClick = { isLoading.value = true }) {
            Text("Fetch Data")
        }
        if (isLoading.value) {
            // Show a loading indicator
            CircularProgressIndicator()
        } else {
            // Show the data
            LazyColumn {
                items(data.value.size) { index ->
                    Text(text = data.value[index], color = MaterialTheme.colors.primary)
                }
            }
        }
    }
}

private suspend fun fetchData(): List<String> {
    // Simulate a network delay
    delay(2000)
    return listOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5",)
}