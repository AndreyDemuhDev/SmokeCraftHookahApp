package com.pidzama.smokecrafthookahapp.presentation.settings

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry


@Composable
fun SettingsScreen(
    navController: NavHostController,
) {
    Text(
        text = "Архив заказов",
        color = MaterialTheme.colors.primary
    )
}




