package com.pidzama.smokecrafthookahapp.presentation.random_generation_recipe.common


import android.graphics.Paint
import android.graphics.Typeface
import android.util.Log
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.navigation.NavHostController
import com.pidzama.smokecrafthookahapp.data.model.RandomRecipeSubList
import com.pidzama.smokecrafthookahapp.navigation.MainScreen
import com.pidzama.smokecrafthookahapp.presentation.common.setColorTaste
import com.pidzama.smokecrafthookahapp.ui.theme.dimens

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MyPieDiagramm(
    input: RandomRecipeSubList,
    animDuration: Int = 600,
    navController: NavHostController,
    indexRecipe: Int
) {
    val density = LocalConfiguration.current.densityDpi
    Log.d("MyLog", "DENSITY ---->$density")

    var circleCenter by remember {
        mutableStateOf(Offset.Zero)
    }

    val firstTasty = 12f
    val secondTasty = 6f
    val threeTasty = 2f
    val listTesty = listOf(firstTasty, secondTasty, threeTasty)
    val totalTasty = firstTasty + secondTasty + threeTasty

    val radius = density / 3.0f
    val innerRadius = radius - ((MaterialTheme.dimens.small1*1.3f).value / 100) * 100
    var animationPlayed by remember { mutableStateOf(false) }
    Log.d("MyLog", "radius ----->$radius")
    Log.d("MyLog", "InnerRadius ----->$innerRadius")
    val animateRotation by animateFloatAsState(
        targetValue = if (animationPlayed) 90f * 12f else 0f, animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = 100,
            easing = LinearOutSlowInEasing
        )
    )
    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = MaterialTheme.dimens.small1)
            .clickable { navController.navigate(MainScreen.DetailHookahScreen.route) },
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
                Column(
                    modifier = Modifier
                        .padding(end = MaterialTheme.dimens.small1)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Рецепт ",
                            color = MaterialTheme.colorScheme.inverseSurface,
                            fontSize = 20.sp,
                        )
                        Text(
                            text = "№${indexRecipe}",
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 20.sp
                        )
                    }
                    input.forEach {
                        Card(
                            modifier = Modifier
                                .padding(vertical = 6.dp),
                            border = BorderStroke(
                                width = 2.dp,
                                color = setColorTaste(it.taste_group)
                            ),
                            shape = MaterialTheme.shapes.medium,
                            backgroundColor = Color.Black
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(horizontal = 8.dp, vertical = 6.dp)
                                    .basicMarquee(),
                                text = "${it.taste}, ${it.brand}",
                                fontWeight = FontWeight.Normal,
                                fontSize = 18.sp,
                                color = setColorTaste(it.taste_group)
                            )
                        }
                    }
                }
            }
            BoxWithConstraints(
                modifier = Modifier
                    .weight(0.5f)
                    .height(height = MaterialTheme.dimens.large3 * 2)
                    .padding(
                        top = MaterialTheme.dimens.large1,
                        start = MaterialTheme.dimens.small2,
                        end = MaterialTheme.dimens.small2,
                    ),
                contentAlignment = Alignment.Center
            ) {

                Canvas(
                    modifier = Modifier.rotate(animateRotation)
                ) {

                    val width = size.width
                    val height = size.height
                    val anglePerValue = 360f / totalTasty
                    var currentStartAngle = 0f

                    circleCenter = Offset(x = width / 2, y = height / 2)

                    listTesty.forEachIndexed { index, testy ->
                        val scale = 1.1f
                        val angleToDraw = testy * anglePerValue

                        scale(scale) {
                            drawArc(
                                color = setColorTaste(input[index].taste_group),
                                startAngle = currentStartAngle,
                                sweepAngle = angleToDraw,
                                useCenter = false,
                                size = Size(
                                    width = radius * 2f,
                                    height = radius * 2f
                                ),
                                topLeft = Offset(
                                    x = (width - radius * 2f) / 2f,
                                    y = (height - radius * 2f) / 2f
                                ),
                                style = Stroke((radius - innerRadius) * 4f, cap = StrokeCap.Butt)
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
                            (testy / totalTasty * 100).toInt()
                        Log.d("MyLog", " ПРОЦЕНТЫ -------->$percentage")
                        drawContext.canvas.nativeCanvas.apply {
                            if (percentage > 3) {
                                rotate(rotateAngle) {
                                    drawText(
                                        "$percentage %",
                                        circleCenter.x,
                                        circleCenter.y + (radius + (density / 10) - (radius - innerRadius - (radius / 2f)) / 2f) * factor,
                                        Paint().apply {
                                            textSize = 15.sp.toPx()
                                            color = setColorTaste(input[index].taste_group).toArgb()
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
    }
}
