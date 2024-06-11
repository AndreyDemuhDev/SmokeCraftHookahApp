package com.pidzama.smokecrafthookahapp.presentation.common

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.center
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pidzama.smokecrafthookahapp.data.model.generate_model.ModelRecipeItem
import com.pidzama.smokecrafthookahapp.utils.converterToWeight
import kotlin.math.cos
import kotlin.math.sin


private const val DividerLengthInDegrees = 1.8f

@OptIn(ExperimentalTextApi::class)
@Composable
fun PieChartRecipeItem(
    input: ModelRecipeItem,
    modifier: Modifier = Modifier
) {
    val currentState = remember {
        MutableTransitionState(AnimatedCircleState.START)
            .apply { targetState = AnimatedCircleState.END }
    }
    val stroke = with(LocalDensity.current) { Stroke(20.dp.toPx()) } //толщина линий диаграммы
    val transition = updateTransition(currentState, label = "") //настрока перехода
    val angleOffset by transition.animateFloat(
        transitionSpec = {
            tween(
                delayMillis = 300,
                durationMillis = 700,
                easing = LinearOutSlowInEasing
            )
        }, label = ""
    ) { stateProgress ->
        if (stateProgress == AnimatedCircleState.START) {
            0f
        } else {
            360f
        }
    } //анимация угла смещения

    val textMeasurer = rememberTextMeasurer()               //настройка отображения текста
    val textMeasureResults = remember(input.taste) {
        input.taste.map {
            textMeasurer.measure(
                text = "${it.weight.toInt()} %",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }

    Canvas(modifier) {
        val innerRadius = (size.minDimension - stroke.width) / 2    //внутренний радиус диаграммы
        val halfSize = size / 2.0f
        val topLeft = Offset(                                       //расположение диаграммы
            halfSize.width - innerRadius,
            halfSize.height - innerRadius
        )
        val size = Size(innerRadius * 2, innerRadius * 2)   //размер диаграммы
        var startAngle = -90f                               //начальный угол отрисовки диаграммы

        input.taste.calculationProportions {
            it.weight.toFloat()
        }.forEachIndexed { index, proportion ->
            val sweep = proportion * angleOffset
            val textMeasureResult = textMeasureResults[index]
            val textSize = textMeasureResult.size
            val textCenter = textSize.center
            val textPosition = ((startAngle + sweep / 2)).degreeToAngle
            drawArc(
                color = setColorTaste(input.matched_tobaccos[index].taste_group),
                startAngle = startAngle + DividerLengthInDegrees / 2,
                sweepAngle = sweep - DividerLengthInDegrees,
                topLeft = topLeft,
                size = size,
                useCenter = false,
                style = stroke
            )

            drawText(
                textLayoutResult = textMeasureResult,
                color = setColorTaste(input.matched_tobaccos[index].taste_group),
                topLeft = Offset(
                    -textCenter.x + center.x + (innerRadius + stroke.width * 1.8f) * cos(
                        textPosition
                    ),
                    -textCenter.y + center.y + (innerRadius + stroke.width * 1.3f) * sin(
                        textPosition
                    )
                )
            )
            startAngle += sweep
        }
    }
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun LegendRecipeItem(
    input: ModelRecipeItem,
    isDetails: Boolean,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.padding(2.dp)) {
        Column(
            verticalArrangement = Arrangement.Top
        ) {
            input.taste.forEachIndexed { index, tabacco ->
                Card(
                    modifier = Modifier
                        .padding(vertical = 4.dp),
                    border = BorderStroke(
                        width = 2.dp,
                        color = setColorTaste(input.matched_tobaccos[index].taste_group)
                    ),
                    shape = MaterialTheme.shapes.small,
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.background
                    )
                ) {
                    Text(
                        buildAnnotatedString {
                            withStyle(
                                SpanStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.W500,
                                    color = setColorTaste(input.matched_tobaccos[index].taste_group)
                                )
                            ) {
                                append("${tabacco.name}, ")
                            }
                            withStyle(
                                SpanStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.W500,
                                    color = setColorTaste(input.matched_tobaccos[index].taste_group)
                                )
                            ) {
                                append(input.matched_tobaccos[index].brand)
                            }
                            if (isDetails) {
                                withStyle(
                                    SpanStyle(
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.W500,
                                        color = Color.White
                                    )
                                ) {
                                    append(
                                        " ${
                                            converterToWeight(
                                                tabacco.weight
                                            )
                                        }"
                                    )
                                }
                            }
                        },
                        modifier = Modifier
                            .padding(
                                horizontal = 8.dp,
                                vertical = 4.dp
                            )
                            .basicMarquee()
                    )
//                    Text(
//                        modifier = Modifier
//                            .padding(
//                                horizontal = 8.dp,
//                                vertical = 4.dp
//                            )
//                            .basicMarquee(),
//                        text = "${tabacco.name}, ${input.matched_tobaccos[index].brand} ${
//                            if (isDetails) {
//                                converterToWeight(
//                                    tabacco.weight
//                                )
//                            } else ""
//                        }",
//                        style = MaterialTheme.typography.labelLarge,
//                        color = setColorTaste(input.matched_tobaccos[index].taste_group)
//                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TitleRecipeItem(
    input: ModelRecipeItem,
    modifier: Modifier = Modifier
) {
    Text(
        buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            ) {
                append("Рецепт:")
            }
            withStyle(
                style = SpanStyle(
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            ) {
                append(" ${input.name}")
            }
        },
        modifier = modifier
            .padding(bottom = 12.dp)
            .basicMarquee(),
        maxLines = 1
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeItemCard(
    input: ModelRecipeItem,
    onClickToDetailsScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedCard(
        elevation = CardDefaults.outlinedCardElevation(4.dp),
        modifier = modifier,
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.outlinedCardColors(containerColor = MaterialTheme.colorScheme.background),
        border = BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.primary),
        onClick = onClickToDetailsScreen
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TitleRecipeItem(input = input, modifier = Modifier.fillMaxWidth())
            Row {
                LegendRecipeItem(input = input, isDetails = false, modifier = Modifier.weight(0.5f))
                PieChartRecipeItem(
                    input = input,
                    modifier = Modifier
                        .size(100.dp)
                        .weight(0.5f)
                        .align(Alignment.CenterVertically)
                )
            }
        }
    }
}

@Composable
fun DetailsRecipeItemCard(
    input: ModelRecipeItem,
    modifier: Modifier = Modifier
) {

    Box(modifier = modifier) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            TitleRecipeItem(input = input, modifier = Modifier.fillMaxWidth())
            LegendRecipeItem(input = input, isDetails = true)
            PieChartRecipeItem(
                input = input,
                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.CenterHorizontally)
                    .weight(0.5f)
            )
        }
    }


}

//подсчет суммы компонентов в рецепте для отрисовки диаграммы
fun <E> List<E>.calculationProportions(component: (E) -> Float): List<Float> {
    val total = this.sumOf { component(it).toDouble() }
    return this.map { (component(it) / total).toFloat() }
}

//перевод градусов в угол
private val Float.degreeToAngle
    get() = (this * Math.PI / 180f).toFloat()

//состояние анимации отрисовки диаграммы
private enum class AnimatedCircleState { START, END }