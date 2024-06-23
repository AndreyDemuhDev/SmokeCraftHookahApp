package com.pidzama.smokecrafthookahapp.presentation.common

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pidzama.smokecrafthookahapp.R
import com.pidzama.smokecrafthookahapp.domain.entities.RecipeModelEntity
import com.pidzama.smokecrafthookahapp.utils.converterWeightToString

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderItemCard(
    input: RecipeModelEntity,
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
        ) {
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        SpanStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.W600,
                            color = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        append(stringResource(id = R.string.recipe))
                    }
                    withStyle(
                        SpanStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.W600,
                            color = MaterialTheme.colorScheme.inverseSurface
                        )
                    ) {
                        append(" ${input.name}")
                    }
                }
            )
            Row(verticalAlignment = Alignment.Top) {
                LegendOrderItem(
                    input = input,
                    isDetails = false,
                    modifier = Modifier.weight(0.6f)
                )
                Image(
                    painter = painterResource(id = R.drawable.hookah),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.inverseSurface),
                    modifier = Modifier.size(120.dp)
                )
            }

        }
    }
}


@Composable
@OptIn(ExperimentalFoundationApi::class)
fun LegendOrderItem(
    input: RecipeModelEntity,
    modifier: Modifier = Modifier,
    isDetails: Boolean = false,
) {
    Log.d("MyLog", "Рецепт в деталях заказа $input")
    Box(modifier = modifier.padding(2.dp)) {
        Column(
            verticalArrangement = Arrangement.Top
        ) {
            input.tasteModel.forEachIndexed { index, tabacco ->
                Card(
                    modifier = Modifier
                        .padding(vertical = 4.dp),
                    border = BorderStroke(
                        width = 2.dp,
                        color = setColorTaste(tabacco.groupTaste)
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
                                    color = setColorTaste(tabacco.groupTaste)
                                )
                            ) {
                                append("${tabacco.nameTaste}, ")
                            }
                            withStyle(
                                SpanStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.W500,
                                    color = MaterialTheme.colorScheme.inverseSurface
                                )
                            ) {
                                append(
                                    " ${
                                        converterWeightToString(
                                            tabacco.weightTaste
                                        )
                                    }"
                                )
                            }
                        },
                        modifier = Modifier
                            .padding(
                                horizontal = 8.dp,
                                vertical = 4.dp
                            )
                            .basicMarquee()
                    )
                }
            }
        }
    }
}