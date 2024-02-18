package com.pidzama.smokecrafthookahapp.presentation.random_generation_recipe.common

import android.graphics.Paint
import android.graphics.Typeface
import android.util.Log
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pidzama.smokecrafthookahapp.R
import com.pidzama.smokecrafthookahapp.data.model.RandomRecipeSubList
import com.pidzama.smokecrafthookahapp.presentation.common.setColorTaste
import com.pidzama.smokecrafthookahapp.ui.theme.dimens


//карточка рецепта при альбомной ориентации
@Composable
fun LandscapeRecipeCard(
    input: RandomRecipeSubList,
    onClickToDetailsScreen: () -> Unit,
    indexRecipe: Int,
    listTobaccoWeight: List<Float>,
    radius: Float
) {

    val scale = remember {
        Animatable(initialValue = 0f)
    }
    LaunchedEffect(true) {
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 200,
                delayMillis = 50,
                easing = LinearEasing
            )
        )
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = MaterialTheme.dimens.small1)
            .scale(scale = scale.value)
            .clickable { onClickToDetailsScreen() },
        border = BorderStroke(2.dp, color = MaterialTheme.colorScheme.primary),
        shape = MaterialTheme.shapes.extraLarge,
        backgroundColor = MaterialTheme.colorScheme.background
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = MaterialTheme.dimens.small3,
                    horizontal = MaterialTheme.dimens.small3
                )
        ) {
            Box(
                modifier = Modifier
                    .weight(0.5f)
            ) {
                LegendRecipeLandscape(
                    input = input,
                    indexRecipe = indexRecipe,
                )
            }
            Box(
                modifier = Modifier
                    .weight(0.5f),
                contentAlignment = Alignment.Center
            ) {
                RecipePieChartLandscape(
                    input = input,
                    listTobaccoWeight = listTobaccoWeight,
                    radius = radius
                )
            }
        }
    }
}

//легенда ингредиентов при альбомной ориентации
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LegendRecipeLandscape(
    input: RandomRecipeSubList,
    indexRecipe: Int,
) {
        Column(
            modifier = Modifier
                .padding(end = MaterialTheme.dimens.extraSmall / 2)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.recipe),
                    color = MaterialTheme.colorScheme.inverseSurface,
                    style = MaterialTheme.typography.titleLarge,
                )
                Text(
                    text = " №${indexRecipe}",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleLarge,
                )
            }
            input.forEach {
                Card(
                    modifier = Modifier
                        .padding(vertical = MaterialTheme.dimens.extraSmall / 2),
                    border = BorderStroke(
                        width = 2.dp,
                        color = setColorTaste(it.taste_group)
                    ),
                    shape = MaterialTheme.shapes.medium,
                    backgroundColor = MaterialTheme.colorScheme.background
                ) {
                    Text(
                        modifier = Modifier
                            .padding(
                                horizontal = MaterialTheme.dimens.small2,
                                vertical = MaterialTheme.dimens.extraSmall
                            )
                            .basicMarquee(),
                        text = "${it.taste}, ${it.brand}",
                        style = MaterialTheme.typography.titleMedium,
                        color = setColorTaste(it.taste_group)
                    )
                }
            }
        }
}

//диаграмма рецепта при альбомной ориентации
@Composable
fun RecipePieChartLandscape(
    input: RandomRecipeSubList,
    listTobaccoWeight: List<Float>,
    animDuration: Int = 600,
    radius: Float
) {
    val density = LocalConfiguration.current.densityDpi
    var circleCenter by remember {
        mutableStateOf(Offset.Zero)
    }
    val coeff: Float
    val pieRadius: Float
    if (density > 400) {
        coeff = 0.65f
        pieRadius = radius * 1.2f
    } else if (density in 261..399) {
        pieRadius = radius / 1.5f
        coeff = 1f
    } else {
        pieRadius = radius / 2f
        coeff = 1.2f
    }

    val totalTastyWeight = listTobaccoWeight.sum()
    val innerRadius = pieRadius - ((MaterialTheme.dimens.small1 * 1.3f).value)
    var animationPlayed by remember { mutableStateOf(false) }
    val animateRotation by animateFloatAsState(
        targetValue = if (animationPlayed) 90f * 12f else 0f, animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = 200,
            easing = LinearOutSlowInEasing
        )
    )
    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

    Box(
        modifier = Modifier
            .padding(top = (innerRadius * coeff).dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .rotate(animateRotation)
        ) {

            val width = size.width
            val height = size.height
            val anglePerValue = 360f / totalTastyWeight
            var currentStartAngle = 270f

            circleCenter = Offset(x = width / 2, y = height / 2)

            listTobaccoWeight.forEachIndexed { index, tobacco ->
                val scale = 1.1f
                val angleToDraw = tobacco * anglePerValue

                scale(scale) {
                    drawArc(
                        color = if (input.size >= 3) {
                            setColorTaste(input[index].taste_group)
                        } else {
                            Color.Red
                        },
                        startAngle = currentStartAngle,
                        sweepAngle = angleToDraw,
                        useCenter = false,
                        size = Size(
                            width = pieRadius * 2f,
                            height = pieRadius * 2f
                        ),
                        topLeft = Offset(
                            x = (width - pieRadius * 2f) / 2f,
                            y = (height - pieRadius * 2f) / 2f
                        ),
                        style = Stroke((pieRadius - innerRadius) * 4f, cap = StrokeCap.Butt)
                    )
                    currentStartAngle += angleToDraw
                }

                var rotateAngle = currentStartAngle - angleToDraw / 2f - 90f
                var factor = 1f
                if (rotateAngle > 90f) {
                    rotateAngle = (rotateAngle + 180).mod(360f)
                    factor = -0.92f
                }

                val percentage =
                    (tobacco / totalTastyWeight * 100).toInt()
                drawContext.canvas.nativeCanvas.apply {
                    if (percentage > 3) {
                        rotate(rotateAngle) {
                            drawText(
                                "$percentage %",
                                circleCenter.x,
                                circleCenter.y + (pieRadius + (innerRadius / 1.5f)) * factor,
                                Paint().apply {
                                    textSize = 17.sp.toPx()
                                    color = if (input.size >= 3) {
                                        setColorTaste(input[index].taste_group).toArgb()
                                    } else {
                                        Color.Red.toArgb()
                                    }
                                    textAlign = Paint.Align.CENTER
                                    typeface = Typeface.DEFAULT_BOLD
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
