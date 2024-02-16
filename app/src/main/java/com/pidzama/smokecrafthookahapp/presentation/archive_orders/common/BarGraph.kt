package com.pidzama.smokecrafthookahapp.presentation.archive_orders.common

import android.util.Log
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import co.yml.charts.axis.AxisData
import co.yml.charts.axis.DataCategoryOptions
import co.yml.charts.common.model.Point
import co.yml.charts.ui.barchart.BarChart
import co.yml.charts.ui.barchart.models.*
import com.pidzama.smokecrafthookahapp.presentation.common.setColorTaste

@Composable
fun YBarChart(
    solution: List<Map.Entry<String, Int>>
) {
    val list = ArrayList<BarData>()
    solution.forEachIndexed { index, entry ->
        list.add(
            BarData(
                point = Point(index.toFloat(), entry.value.toFloat()),
                label = entry.key,
                color = setColorTaste(entry.key),
                dataCategoryOptions = DataCategoryOptions()
            )
        )
    }
    Log.d("MyLog", "SOLUTION SIZW ${solution.size}")

    val stepSize = list.size

    val max = getMax(list)
    val min = getMin(list)

    val xAxisData = AxisData.Builder()
        .axisStepSize(40.dp)
        .backgroundColor(MaterialTheme.colorScheme.primary)
        .steps(list.size - 1)
        .bottomPadding(50.dp)
        .axisLabelAngle(50f)
        .labelData { index -> list[index].label }
        .labelAndAxisLinePadding(5.dp)
        .build()

    val yAxisData = AxisData.Builder()
        .steps(stepSize)
        .backgroundColor(MaterialTheme.colorScheme.primary)
        .labelAndAxisLinePadding(10.dp)
        .axisOffset(20.dp)
        .labelData { index ->
            val yScale = (max - min) * 2 / stepSize.toFloat()
            Log.d("MyLog", "index $index")
            Log.d("MyLog", "LIST SIZE ${list.size}")
            Log.d("MyLog", "MAX $max")
//            Log.d("MyLog", "yScale $yScale")
            (((index * yScale)).toString())
        }
        .build()


    val barChartData = BarChartData(
        chartData = list,
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        backgroundColor = MaterialTheme.colorScheme.background,
        horizontalExtraSpace = 20.dp,
        paddingTop = 50.dp,
        barStyle = BarStyle(
            selectionHighlightData = SelectionHighlightData(
                highlightBarColor = MaterialTheme.colorScheme.primary,
                highlightTextColor = MaterialTheme.colorScheme.primary,
                highlightTextBackgroundColor = MaterialTheme.colorScheme.background.copy(alpha = 0.0f),
//                popUpLabel ={},

            )
        )
//        barWidth = 25.dp
    )

    BarChart(modifier = Modifier.height(350.dp), barChartData = barChartData)
}


private fun getMax(list: List<BarData>): Float {
    var max = 0F
    list.forEach { value ->
        if (max < value.point.y) {
            max = value.point.y
        }
    }
    return max
}

private fun getMin(list: List<BarData>): Float {
    var min = 100F
    list.forEach { value ->
        if (min > value.point.y) {
            min = value.point.y
        }
    }
    return min
}