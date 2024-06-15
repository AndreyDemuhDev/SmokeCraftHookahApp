package com.pidzama.smokecrafthookahapp.presentation.detail_hookah.common

import android.graphics.Paint
import android.graphics.Typeface
import android.util.Log
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
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
import com.pidzama.smokecrafthookahapp.data.model.generate_model.ModelRecipeItem
import com.pidzama.smokecrafthookahapp.presentation.common.setColorTaste
import com.pidzama.smokecrafthookahapp.ui.theme.dimens
import com.pidzama.smokecrafthookahapp.utils.converterWeightToString

@Composable
fun PortraitDetailRecipeCard(
    input: ModelRecipeItem,
    numberRecipe: Int,
    radius: Float
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LegendDetailRecipePortrait(
            input = input,
            indexRecipe = numberRecipe,
        )
        Spacer(modifier = Modifier.padding(MaterialTheme.dimens.small1))
        PortraitDetailRecipePieChart(
            input = input,
            radius = radius
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LegendDetailRecipePortrait(
    input: ModelRecipeItem,
    indexRecipe: Int,
) {
    Column(
        modifier = Modifier
            .padding(end = MaterialTheme.dimens.small1)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.recipe),
                color = MaterialTheme.colorScheme.inverseSurface,
                style = MaterialTheme.typography.headlineMedium,
            )
            Text(
                text = " №${indexRecipe}",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.headlineMedium,
            )
        }
        input.taste.forEachIndexed { index, tobacco ->
            Card(
                modifier = Modifier
                    .padding(vertical = MaterialTheme.dimens.extraSmall),
                border = BorderStroke(
                    width = 2.dp,
                    color = setColorTaste(input.matched_tobaccos[index].taste_group)
                ),
                shape = MaterialTheme.shapes.medium,
                backgroundColor = MaterialTheme.colorScheme.background
            ) {
                Row(modifier = Modifier.basicMarquee()) {
                    Text(
                        modifier = Modifier
                            .padding(
                                horizontal = MaterialTheme.dimens.small2,
                                vertical = MaterialTheme.dimens.extraSmall
                            ),
                        text = "${tobacco.name}, ${input.matched_tobaccos[index].brand}",
                        style = MaterialTheme.typography.titleLarge,
                        color = setColorTaste(input.matched_tobaccos[index].taste_group)
                    )
                    Text(
                        modifier = Modifier.padding(
                            top = MaterialTheme.dimens.extraSmall,
                            bottom = MaterialTheme.dimens.extraSmall,
                            end = MaterialTheme.dimens.small2
                        ),
                        text = converterWeightToString(input.taste[index].weight),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.outlineVariant
                    )
                }
            }
        }
    }
}


@Composable
fun PortraitDetailRecipePieChart(
    input: ModelRecipeItem,
    animDuration: Int = 600,
    radius: Float,
) {
    val density = LocalConfiguration.current.densityDpi
    var circleCenter by remember {
        mutableStateOf(Offset.Zero)
    }
    val coeff: Float
    val pieRadius: Float
    if (density > 400) {
        coeff = 0.85f
        pieRadius = radius * 1.6f
    } else if (density in 261..399) {
        coeff = 1f
        pieRadius = radius / 1.5f
    } else {
        coeff = 1.3f
        pieRadius = radius / 1.8f
    }
    Log.d("MyLog", "DENSITY == > $density")
    val totalTastyWeight = input.taste.sumOf { it.weight }
    val innerRadius = pieRadius - ((MaterialTheme.dimens.small3).value)
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
            .padding(top = (innerRadius * coeff).dp)
            .background(Color.Red),
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

            input.taste.forEachIndexed { index, tobacco ->
                val scale = 1.1f
                val angleToDraw = tobacco.weight * anglePerValue

                scale(scale) {
                    drawArc(
                        color = setColorTaste(input.matched_tobaccos[index].taste_group),
                        startAngle = currentStartAngle,
                        sweepAngle = angleToDraw.toFloat(),
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
                    currentStartAngle += angleToDraw.toFloat()
                }

                var rotateAngle = currentStartAngle - angleToDraw / 2f - 90f
                var factor = 1f
                if (rotateAngle > 90f) {
                    rotateAngle = (rotateAngle + 180).mod(360f)
                    factor = -0.92f
                }
                val percentage =
                    (tobacco.weight / totalTastyWeight * 100).toInt()
                drawContext.canvas.nativeCanvas.apply {
                    if (percentage > 3) {
                        rotate(rotateAngle.toFloat()) {
                            drawText(
                                "$percentage %",
                                circleCenter.x,
                                circleCenter.y + (pieRadius + innerRadius / 1.7f) * factor,
                                Paint().apply {
                                    textSize = 20.sp.toPx()
                                    color =
                                        setColorTaste(input.matched_tobaccos[index].taste_group).toArgb()
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