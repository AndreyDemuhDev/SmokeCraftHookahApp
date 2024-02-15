package com.pidzama.smokecrafthookahapp.presentation.detail_hookah

import android.graphics.Paint
import android.graphics.Typeface
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
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
import com.pidzama.smokecrafthookahapp.data.model.RandomRecipeSubList
import com.pidzama.smokecrafthookahapp.presentation.common.setColorTaste
import com.pidzama.smokecrafthookahapp.ui.theme.dimens
//
//@OptIn(ExperimentalFoundationApi::class)
//@Composable
//fun PortraitDetailPieChart(
//    input: RandomRecipeSubList,
//    numberRecipe: Int,
//    animDuration: Int = 400,
//    listTobaccoWeight: List<Float>
//) {
//
//    val screenWidth = LocalConfiguration.current.screenWidthDp
//    val density = LocalConfiguration.current.densityDpi
//    var circleCenter by remember {
//        mutableStateOf(Offset.Zero)
//    }
//    val radius = density / 1.5f
//    val innerRadius = radius - ((MaterialTheme.dimens.small2).value / 100) * 100
//    val totalTastyWeight = listTobaccoWeight.sum()
//
//    var animationPlayed by remember { mutableStateOf(false) }
//
//    val animateRotation by animateFloatAsState(
//        targetValue = if (animationPlayed) 90f * 12f else 0f, animationSpec = tween(
//            durationMillis = animDuration,
//            delayMillis = 100,
//            easing = LinearOutSlowInEasing
//        )
//    )
//    LaunchedEffect(key1 = true) {
//        animationPlayed = true
//    }
//
//    LazyColumn(
//        modifier = Modifier.fillMaxSize()
//    ) {
//        item {
//            Row(
//                modifier = Modifier.padding(bottom = MaterialTheme.dimens.small3),
//            ) {
//                Text(
//                    text = stringResource(id = R.string.recipe),
//                    color = MaterialTheme.colorScheme.inverseSurface,
//                    style = MaterialTheme.typography.headlineMedium,
//                )
//                Text(
//                    text = " №$numberRecipe",
//                    color = MaterialTheme.colorScheme.primary,
//                    style = MaterialTheme.typography.headlineMedium,
//                )
//            }
//            input.forEachIndexed { index, tasty ->
//                Card(
//                    modifier = Modifier.padding(vertical = MaterialTheme.dimens.extraSmall),
//                    border = BorderStroke(2.dp, color = setColorTaste(tasty.taste_group)),
//                    shape = MaterialTheme.shapes.medium,
//                    backgroundColor = MaterialTheme.colorScheme.background
//                ) {
//                    Row(modifier = Modifier.basicMarquee()) {
//                        Text(
//                            modifier = Modifier.padding(
//                                top = MaterialTheme.dimens.extraSmall,
//                                bottom = MaterialTheme.dimens.extraSmall,
//                                start = MaterialTheme.dimens.small2,
//                                end = MaterialTheme.dimens.small2
//                            ),
//                            text = "${tasty.taste}, ${tasty.brand}",
//                            style = MaterialTheme.typography.titleLarge,
//                            color = setColorTaste(tasty.taste_group)
//                        )
//                        Text(
//                            modifier = Modifier.padding(
//                                top = MaterialTheme.dimens.extraSmall,
//                                bottom = MaterialTheme.dimens.extraSmall,
//                                start = MaterialTheme.dimens.extraSmall,
//                                end = MaterialTheme.dimens.small2
//                            ),
//                            text = "${listTobaccoWeight[index].toInt()} г.",
//                            style = MaterialTheme.typography.titleLarge,
//                            color = MaterialTheme.colorScheme.outlineVariant
//                        )
//                    }
//                }
//            }
//            BoxWithConstraints(
//                modifier = Modifier
//                    .height(height = (screenWidth - (MaterialTheme.dimens.large2.value)).dp)
//                    .fillMaxWidth()
//                    .padding(
//                        top = MaterialTheme.dimens.large3,
//                        bottom = MaterialTheme.dimens.large3,
//                        start = MaterialTheme.dimens.small2,
//                        end = MaterialTheme.dimens.small2,
//                    ),
//                contentAlignment = Alignment.Center
//            ) {
//                Canvas(
//                    modifier = Modifier.rotate(animateRotation)
//                ) {
//
//                    val width = size.width
//                    val height = size.height
//                    val anglePerValue = 360f / totalTastyWeight
//                    var currentStartAngle = 270f
//                    circleCenter = Offset(x = width / 2, y = height / 2)
//
//                    listTobaccoWeight.forEachIndexed { index, tobacco ->
//                        val scale = 1.1f
//                        val angleToDraw = tobacco * anglePerValue
//
//                        scale(scale) {
//                            drawArc(
//                                color = if (input.size >= 3) {
//                                    setColorTaste(input[index].taste_group)
//                                } else {
//                                    Color.Red
//                                },
//                                startAngle = currentStartAngle,
//                                sweepAngle = angleToDraw,
//                                useCenter = false,
//                                size = Size(
//                                    width = radius * 2f,
//                                    height = radius * 2f
//                                ),
//                                topLeft = Offset(
//                                    x = (width - radius * 2f) / 2f,
//                                    y = (height - radius * 2f) / 2f
//                                ),
//                                style = Stroke((radius - innerRadius) * 4f, cap = StrokeCap.Butt)
//                            )
//                            currentStartAngle += angleToDraw
//                        }
//
//                        var rotateAngle = currentStartAngle - angleToDraw / 2f - 90f
//                        var factor = 1f
//                        if (rotateAngle > 90f) {
//                            rotateAngle = (rotateAngle + 180).mod(360f)
//                            factor = -0.92f
//                        }
//                        val percentage =
//                            (tobacco / totalTastyWeight * 100).toInt()
//                        drawContext.canvas.nativeCanvas.apply {
//                            if (percentage > 3) {
//                                rotate(rotateAngle) {
//                                    drawText(
//                                        "$percentage %",
//                                        circleCenter.x,
//                                        circleCenter.y + (radius + (density / 10) - (radius - innerRadius - (radius / 2f)) / 2f) * factor,
//                                        Paint().apply {
//                                            textSize = 15.sp.toPx()
//                                            color = if (input.size >= 3) {
//                                                setColorTaste(input[index].taste_group).toArgb()
//                                            } else {
//                                                Color.Yellow.toArgb()
//                                            }
//                                            textAlign = Paint.Align.CENTER
//                                            typeface = Typeface.DEFAULT_BOLD
//                                        }
//                                    )
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//}
//
//
//@OptIn(ExperimentalFoundationApi::class)
//@Composable
//fun LandscapeDetailPieChart(
//    input: RandomRecipeSubList,
//    numberRecipe: Int,
//    animDuration: Int = 400,
//    listTobaccoWeight: List<Float>
//) {
//    val density = LocalConfiguration.current.densityDpi
//    var circleCenter by remember {
//        mutableStateOf(Offset.Zero)
//    }
//    val radius = density / 2.2f
//    val innerRadius = radius - ((MaterialTheme.dimens.small2).value / 100) * 100
//    val totalTastyWeight = listTobaccoWeight.sum()
//    var animationPlayed by remember { mutableStateOf(false) }
//
//    val animateRotation by animateFloatAsState(
//        targetValue = if (animationPlayed) 90f * 12f else 0f, animationSpec = tween(
//            durationMillis = animDuration,
//            delayMillis = 100,
//            easing = LinearOutSlowInEasing
//        )
//    )
//    LaunchedEffect(key1 = true) {
//        animationPlayed = true
//    }
//
//    LazyRow(
//        modifier = Modifier.fillMaxSize()
//    ) {
//        item {
//            Column() {
//                Row(
//                    modifier = Modifier.padding(bottom = MaterialTheme.dimens.extraSmall / 2),
//                ) {
//                    Text(
//                        text = stringResource(id = R.string.recipe),
//                        color = MaterialTheme.colorScheme.inverseSurface,
//                        style = MaterialTheme.typography.titleLarge,
//                    )
//                    Text(
//                        text = " №${numberRecipe}",
//                        color = MaterialTheme.colorScheme.primary,
//                        style = MaterialTheme.typography.titleLarge,
//                    )
//                }
//                input.forEachIndexed { index, tobacco ->
//                    Card(
//                        modifier = Modifier.padding(vertical = MaterialTheme.dimens.extraSmall / 2),
//                        border = BorderStroke(2.dp, color = setColorTaste(tobacco.taste_group)),
//                        shape = MaterialTheme.shapes.medium,
//                        backgroundColor = MaterialTheme.colorScheme.background
//                    ) {
//                        Row(modifier = Modifier.basicMarquee()) {
//                            Text(
//                                modifier = Modifier.padding(
//                                    all = MaterialTheme.dimens.extraSmall
//                                ),
//                                text = "${tobacco.taste}, ${tobacco.brand}",
//                                style = MaterialTheme.typography.titleMedium,
//                                color = setColorTaste(tobacco.taste_group)
//                            )
//                            Text(
//                                modifier = Modifier.padding(
//                                    all = MaterialTheme.dimens.extraSmall
//                                ),
//                                text = "${listTobaccoWeight[index].toInt()} г.",
//                                style = MaterialTheme.typography.titleMedium,
//                                color = MaterialTheme.colorScheme.outlineVariant
//                            )
//                        }
//                    }
//                }
//            }
//            BoxWithConstraints(
//                modifier = Modifier
//                    .width(width = (radius * 2).dp)
//                    .fillMaxWidth()
//                    .padding(
//                        top = (MaterialTheme.dimens.medium3.value * 2.3).dp
//                    ),
//                contentAlignment = Alignment.Center
//            ) {
//                Canvas(
//                    modifier = Modifier.rotate(animateRotation)
//                ) {
//
//                    val width = size.width
//                    val height = size.height
//                    val anglePerValue = 360f / totalTastyWeight
//                    var currentStartAngle = 270f
//                    circleCenter = Offset(x = width / 2, y = height / 2)
//
//                    listTobaccoWeight.forEachIndexed { index, testy ->
//                        val scale = 1.1f
//                        val angleToDraw = testy * anglePerValue
//
//                        scale(scale) {
//                            drawArc(
//                                color = setColorTaste(input[index].taste_group),
//                                startAngle = currentStartAngle,
//                                sweepAngle = angleToDraw,
//                                useCenter = false,
//                                size = Size(
//                                    width = radius * 2f,
//                                    height = radius * 2f
//                                ),
//                                topLeft = Offset(
//                                    x = (width - radius * 2f) / 2f,
//                                    y = (height - radius * 2f) / 2f
//                                ),
//                                style = Stroke((radius - innerRadius) * 4f, cap = StrokeCap.Butt)
//                            )
//                            currentStartAngle += angleToDraw
//                        }
//
//                        var rotateAngle = currentStartAngle - angleToDraw / 2f - 90f
//                        var factor = 1f
//                        if (rotateAngle > 90f) {
//                            rotateAngle = (rotateAngle + 180).mod(360f)
//                            factor = -0.92f
//                        }
//                        val percentage =
//                            (testy / totalTastyWeight * 100).toInt()
//                        drawContext.canvas.nativeCanvas.apply {
//                            if (percentage > 3) {
//                                rotate(rotateAngle) {
//                                    drawText(
//                                        "$percentage %",
//                                        circleCenter.x,
//                                        circleCenter.y + (radius + (density / 7) - (radius - innerRadius - (radius / 2f)) / 2f) * factor,
//                                        Paint().apply {
//                                            textSize = 18.sp.toPx()
//                                            color = setColorTaste(input[index].taste_group).toArgb()
//                                            textAlign = Paint.Align.CENTER
//                                            typeface = Typeface.DEFAULT_BOLD
//                                        }
//                                    )
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//}