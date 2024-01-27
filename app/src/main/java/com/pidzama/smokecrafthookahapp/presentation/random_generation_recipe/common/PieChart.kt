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
import com.pidzama.smokecrafthookahapp.ui.theme.dimens

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MyPieDiagramm(
    input: RandomRecipeSubList,
    animDuration: Int = 600,
    navController: NavHostController,
    indexRecipe: Int
) {
    Log.d("MyLog", "INPUT LIST --->${input}")
    val density = LocalConfiguration.current.densityDpi

    var circleCenter by remember {
        mutableStateOf(Offset.Zero)
    }

    val firstTasty = 12f
    val secondTasty = 6f
    val threeTasty = 2f
    val listTesty = listOf(firstTasty, secondTasty, threeTasty)
    val totalTasty = firstTasty + secondTasty + threeTasty

    val radius = density / 2.5f
    val innerRadius = radius - ((MaterialTheme.dimens.small2).value / 100) * 100
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
                                color = when (it.taste_group) {
                                    "Фруктовые" -> {
                                        Color(0xFFEB1B32)
                                    }
                                    "Ягодные" -> {
                                        Color(0xFF924EFF)
                                    }
                                    "Цитрусовые" -> {
                                        Color(0xFFF4EA00)
                                    }
                                    "Алкогольные" -> {
                                        Color(0xFF65FCCF)
                                    }
                                    "Пряные" -> {
                                        Color(0xFFEDC894)
                                    }
                                    "Десертные" -> {
                                        Color(0xFFFF6680)
                                    }
                                    "Травянистые" -> {
                                        Color(0xFF6CA314)
                                    }
                                    "Ореховые" -> {
                                        Color(0xFFBF6614)
                                    }
                                    "Цветочные" -> {
                                        Color(0xFFCB7DE1)
                                    }
                                    else -> {
                                        Color(0xFFFAFAFA)
                                    }
                                }
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
                                color = when (it.taste_group) {
                                    "Фруктовые" -> {
                                        Color(0xFFEB1B32)
                                    }
                                    "Ягодные" -> {
                                        Color(0xFF924EFF)
                                    }
                                    "Цитрусовые" -> {
                                        Color(0xFFF4EA00)
                                    }
                                    "Алкогольные" -> {
                                        Color(0xFF65FCCF)
                                    }
                                    "Пряные" -> {
                                        Color(0xFFEDC894)
                                    }
                                    "Десертные" -> {
                                        Color(0xFFFF6680)
                                    }
                                    "Травянистые" -> {
                                        Color(0xFF6CA314)
                                    }
                                    "Ореховые" -> {
                                        Color(0xFFBF6614)
                                    }
                                    "Цветочные" -> {
                                        Color(0xFFCB7DE1)
                                    }
                                    else -> {
                                        Color(0xFFFAFAFA)
                                    }
                                }
                            )
                        }
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
                    var currentStartAngle = 300f

                    listTesty.forEachIndexed {index , testy ->
                        val scale = 1.1f
                        val angleToDraw = testy * anglePerValue

                        scale(scale) {
                            drawArc(
                                color = when (input[index].taste_group) {
                                    "Фруктовые" -> {
                                        Color(0xFFEB1B32)
                                    }
                                    "Ягодные" -> {
                                        Color(0xFF924EFF)
                                    }
                                    "Цитрусовые" -> {
                                        Color(0xFFF4EA00)
                                    }
                                    "Алкогольные" -> {
                                        Color(0xFF65FCCF)
                                    }
                                    "Пряные" -> {
                                        Color(0xFFEDC894)
                                    }
                                    "Десертные" -> {
                                        Color(0xFFFF6680)
                                    }
                                    "Травянистые" -> {
                                        Color(0xFF6CA314)
                                    }
                                    "Ореховые" -> {
                                        Color(0xFFBF6614)
                                    }
                                    "Цветочные" -> {
                                        Color(0xFFCB7DE1)
                                    }
                                    else -> {
                                        Color(0xFFFAFAFA)
                                    }
                                },
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
                            (testy / totalTasty.toFloat() * 100).toInt()
                        drawContext.canvas.nativeCanvas.apply {
                            if (percentage > 3) {
                                rotate(rotateAngle.toFloat()) {
                                    drawText(
                                        "$percentage %",
                                        circleCenter.x,
                                        circleCenter.y + (radius - (radius - innerRadius - (radius / 2f)) / 2f) * factor,
                                        Paint().apply {
                                            textSize = 15.sp.toPx()
                                            color = when (input[index].taste_group) {
                                                "Фруктовые" -> {
                                                    Color(0xFFEB1B32).toArgb()
                                                }
                                                "Ягодные" -> {
                                                    Color(0xFF924EFF).toArgb()
                                                }
                                                "Цитрусовые" -> {
                                                    Color(0xFFF4EA00).toArgb()
                                                }
                                                "Алкогольные" -> {
                                                    Color(0xFF65FCCF).toArgb()
                                                }
                                                "Пряные" -> {
                                                    Color(0xFFEDC894).toArgb()
                                                }
                                                "Десертные" -> {
                                                    Color(0xFFFF6680).toArgb()
                                                }
                                                "Травянистые" -> {
                                                    Color(0xFF6CA314).toArgb()
                                                }
                                                "Ореховые" -> {
                                                    Color(0xFFBF6614).toArgb()
                                                }
                                                "Цветочные" -> {
                                                    Color(0xFFCB7DE1).toArgb()
                                                }
                                                else -> {
                                                    Color(0xFFFAFAFA).toArgb()
                                                }
                                            }
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
