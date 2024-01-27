package com.pidzama.smokecrafthookahapp.presentation.settings

import android.graphics.Paint
import android.graphics.Typeface
import android.util.Log
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
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
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.pidzama.smokecrafthookahapp.data.model.RandomRecipeSubList
import com.pidzama.smokecrafthookahapp.navigation.MainScreen
import com.pidzama.smokecrafthookahapp.ui.theme.dimens

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PIEPIECHART(
    input: RandomRecipeSubList,
    animDuration: Int = 600,
    navController: NavHostController
) {
    Log.d("MyLog", "INPUT LIST --->${input}")
    val density = LocalConfiguration.current.densityDpi

    val firstTasty = 16f
    val secondTasty = 8f
    val threeTasty = 2f

    val listTesty = listOf(firstTasty, secondTasty, threeTasty)

    val totalTasty = firstTasty + secondTasty + threeTasty

    val firstRecipe = input[0]
    val secondRecipe = input[1]
    val threeRecipe = input[2]

//    input.forEachIndexed { index, recipe ->
        var circleCenter by remember {
            mutableStateOf(Offset.Zero)
        }
        val colores = if (firstRecipe.taste_group == "Травянистые" ||secondRecipe.taste_group == "Травянистые" || threeRecipe.taste_group == "Травянистые") {
            Color.Blue
        } else if (firstRecipe.taste_group == "Цветочные" ||secondRecipe.taste_group == "Цветочные" || threeRecipe.taste_group == "Цветочные") {
            Color.Yellow
        } else if (firstRecipe.taste_group == "Фруктовые" ||secondRecipe.taste_group == "Фруктовые" || threeRecipe.taste_group == "Фруктовые") {
            Color.Green
        } else if (firstRecipe.taste_group == "Алкогольные" ||secondRecipe.taste_group == "Алкогольные" || threeRecipe.taste_group == "Алкогольные") {
            Color.LightGray
        } else if (firstRecipe.taste_group == "Ягодные" ||secondRecipe.taste_group == "Ягодные" || threeRecipe.taste_group == "Ягодные") {
            Color.Red
        } else if (firstRecipe.taste_group == "Цитрусовые" ||secondRecipe.taste_group == "Цитрусовые" || threeRecipe.taste_group == "Цитрусовые") {
            Color.Magenta
        } else {
            Color.DarkGray
        }

        val radius = density / 2.5f
        val innerRadius = radius - ((MaterialTheme.dimens.small2).value / 100) * 100


//        Log.d("MyLog", "INPUT LIST[INDEX] --->${input[index]}")
//        Log.d("MyLog", "RECIPE --->${recipe}")
//        Log.d("MyLog", "INDEX --->${index}")

        var animationPlayed by remember { mutableStateOf(false) }

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
                    Column() {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Рецепт ",
                                color = MaterialTheme.colorScheme.inverseSurface,
                                fontSize = 20.sp,
                            )
                            Text(
                                text = "№${1}",
                                color = MaterialTheme.colorScheme.primary,
                                fontSize = 20.sp
                            )
                        }

                            Card(
                                modifier = Modifier
                                    .padding(vertical = 6.dp),
                                border = BorderStroke(2.dp, color = colores),
                                shape = MaterialTheme.shapes.medium,
                                backgroundColor = Color.Black
                            ) {
                                Text(
                                    modifier = Modifier
                                        .padding(horizontal = 8.dp, vertical = 6.dp)
                                        .basicMarquee(),
                                    text = "${firstRecipe.taste}, ${firstRecipe.brand}",
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 18.sp,
                                    color = colores
                                )
                            }
                            Card(
                                modifier = Modifier
                                    .padding(vertical = 6.dp),
                                border = BorderStroke(2.dp, color = colores),
                                shape = MaterialTheme.shapes.medium,
                                backgroundColor = Color.Black
                            ) {
                                Text(
                                    modifier = Modifier
                                        .padding(horizontal = 8.dp, vertical = 6.dp)
                                        .basicMarquee(),
                                    text = "${secondRecipe.taste}, ${secondRecipe.brand}",
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 18.sp,
                                    color = colores
                                )
                            }
                            Card(
                                modifier = Modifier
                                    .padding(vertical = 6.dp),
                                border = BorderStroke(2.dp, color = colores),
                                shape = MaterialTheme.shapes.medium,
                                backgroundColor = Color.Black
                            ) {
                                Text(
                                    modifier = Modifier
                                        .padding(horizontal = 8.dp, vertical = 6.dp)
                                        .basicMarquee(),
                                    text = "${threeRecipe.taste}, ${threeRecipe.brand}",
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 18.sp,
                                    color = colores
                                )
                            }
                        }

                }
                BoxWithConstraints(
                    modifier = Modifier
                        .weight(0.5f)
                        .height(height = 140.dp)
                        .padding(top = 40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(
                        modifier = Modifier.rotate(animateRotation)
                    ) {

                        val width = size.width
                        val height = size.height
                        circleCenter = Offset(x = width / 2, y = height / 2)

                        val totalValue = input.sumOf {
                            it.weight
                        }
                        val anglePerValue = 360f / totalTasty
                        var currentStartAngle = 0f

                        listTesty.forEach { recipe ->
                            val scale = if (true) 1.1f else 1.0f
                            val angleToDraw = recipe * anglePerValue


                            scale(scale) {
                                drawArc(
                                    color = colores,
                                    startAngle = currentStartAngle,
                                    sweepAngle = angleToDraw.toFloat(),
                                    useCenter = true,
                                    size = Size(
                                        width = radius * 2f,
                                        height = radius * 2f
                                    ),
                                    topLeft = Offset(
                                        x = (width - radius * 2f) / 2f,
                                        y = (height - radius * 2f) / 2f
                                    )
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
                                (recipe / totalTasty.toFloat() * 100).toInt()
                            drawContext.canvas.nativeCanvas.apply {
                                if (percentage > 3) {
                                    rotate(rotateAngle.toFloat()) {
                                        drawText(
                                            "$percentage %",
                                            circleCenter.x,
                                            circleCenter.y + (radius - (radius - innerRadius - (radius / 2f)) / 2f) * factor,
                                            Paint().apply {
                                                textSize = 15.sp.toPx()
                                                color = colores.toArgb()
                                                textAlign = Paint.Align.CENTER
                                                typeface = Typeface.DEFAULT_BOLD
                                            }
                                        )
                                    }
                                }
                            }
                        }

                        drawContext.canvas.nativeCanvas.apply {
                            drawCircle(
                                circleCenter.x,
                                circleCenter.y,
                                innerRadius / 1.5f,
                                Paint().apply {
                                    color = Color.Black.toArgb()
                                    setShadowLayer(50f, 0f, 0f, Color.Gray.toArgb())
                                }
                            )
                        }
                        drawCircle(
                            color = Color.Transparent.copy(0.2f),
                            radius = innerRadius * 0f / 4f
                        )
                    }
                }
            }
        }
    }
//}