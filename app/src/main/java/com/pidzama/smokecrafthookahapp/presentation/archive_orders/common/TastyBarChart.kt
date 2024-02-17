package com.pidzama.smokecrafthookahapp.presentation.archive_orders.common

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
fun TastyBarChart(
    listTobaccos: List<Map.Entry<String, Int>>
) {
    val list = ArrayList<BarData>()
    listTobaccos.forEachIndexed { index, entry ->
        list.add(
            BarData(
                point = Point(index.toFloat(), entry.value.toFloat()),
                label = entry.key,
                color = setColorTaste(entry.key),
                dataCategoryOptions = DataCategoryOptions()
            )
        )
    }

    val stepSize = list.size
    val max = getMaxY(list)

    val xAxisData = AxisData.Builder()
        .startDrawPadding(18.dp)
        .axisLineColor(MaterialTheme.colorScheme.inverseSurface)
        .axisStepSize(10.dp)
        .axisLabelAngle(45f)
        .axisOffset(2.dp)
        .axisLabelColor(MaterialTheme.colorScheme.inverseSurface)
        .backgroundColor(MaterialTheme.colorScheme.background)
        .steps(list.size - 1)
        .labelData { index -> list[index].label }
        .bottomPadding(140.dp)
        .build()

    val yAxisData = AxisData.Builder()
        .steps(stepSize)
        .backgroundColor(MaterialTheme.colorScheme.background)
        .axisLineColor(MaterialTheme.colorScheme.inverseSurface)
        .labelAndAxisLinePadding(10.dp)
        .axisLabelColor(MaterialTheme.colorScheme.inverseSurface)
        .axisOffset(30.dp)
        .labelData { index ->
            val yScale = max / stepSize.toFloat()
            String.format("%.0f", (((index * yScale))))
        }
        .build()


    val barChartData = BarChartData(
        chartData = list,
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        backgroundColor = MaterialTheme.colorScheme.background,
        paddingTop = 10.dp,
        paddingEnd = 3.dp,
        barStyle = BarStyle(
            selectionHighlightData = SelectionHighlightData(
                highlightBarColor = MaterialTheme.colorScheme.inverseSurface,
                highlightTextColor = MaterialTheme.colorScheme.inverseSurface,
                highlightTextBackgroundColor = MaterialTheme.colorScheme.background.copy(alpha = 0.0f),
                popUpLabel = { x, y -> "${list[x.toInt()].label}, ${y.toInt()}" }
            )
        ),
    )

    BarChart(modifier = Modifier.height(350.dp), barChartData = barChartData)
}

private fun getMaxY(list: List<BarData>): Float {
    var max = list.size.toFloat()
    list.forEach { value ->
        if (max < value.point.y) {
            max = value.point.y
        }
    }
    return max
}
