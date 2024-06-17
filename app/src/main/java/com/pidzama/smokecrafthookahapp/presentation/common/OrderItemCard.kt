package com.pidzama.smokecrafthookahapp.presentation.common

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pidzama.smokecrafthookahapp.R
import com.pidzama.smokecrafthookahapp.data.model.generate_model.Taste
import com.pidzama.smokecrafthookahapp.data.remote.order.OrderResponse
import com.pidzama.smokecrafthookahapp.utils.converterWeightToString

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderItemCard(
    input: OrderResponse,
    @StringRes title: Int,
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
                text = stringResource(id = title),
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Row(verticalAlignment = Alignment.Top) {
                LegendOrderItem(
                    input = input.recipes,
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
    input: List<Taste>,
    isDetails: Boolean,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.padding(2.dp)) {
        Column(
            verticalArrangement = Arrangement.Top
        ) {
            input.forEachIndexed { index, tabacco ->
                Card(
                    modifier = Modifier
                        .padding(vertical = 4.dp),
                    border = BorderStroke(
                        width = 2.dp,
                        color = setColorTaste(tabacco.name)
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
                                    color = setColorTaste(tabacco.name)
                                )
                            ) {
                                append("${tabacco.name}, ")
                            }
                            withStyle(
                                SpanStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.W500,
                                    color = setColorTaste(tabacco.name)
                                )
                            ) {
                                append(tabacco.name)
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
                                            converterWeightToString(
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
                }
            }
        }
    }
}