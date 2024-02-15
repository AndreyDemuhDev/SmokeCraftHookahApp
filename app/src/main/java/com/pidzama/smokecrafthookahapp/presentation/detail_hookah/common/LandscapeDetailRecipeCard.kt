package com.pidzama.smokecrafthookahapp.presentation.detail_hookah.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.pidzama.smokecrafthookahapp.R
import com.pidzama.smokecrafthookahapp.data.model.RandomRecipeSubList
import com.pidzama.smokecrafthookahapp.presentation.common.setColorTaste
import com.pidzama.smokecrafthookahapp.presentation.random_generation_recipe.common.RecipePieChartLandscape
import com.pidzama.smokecrafthookahapp.ui.theme.dimens

@Composable
fun LandscapeDetailRecipeCard(
    input: RandomRecipeSubList,
    numberRecipe: Int,
    listTobaccoWeight: List<Float>,
    radius: Float
) {
    Row(
        modifier = Modifier.fillMaxSize()
    ) {
        LegendDetailRecipeLandscape(
            input = input,
            indexRecipe = numberRecipe,
            listTobaccoWeight = listTobaccoWeight
        )
        RecipePieChartLandscape(
            input = input,
            listTobaccoWeight = listTobaccoWeight,
            radius = radius
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LegendDetailRecipeLandscape(
    input: RandomRecipeSubList,
    indexRecipe: Int,
    listTobaccoWeight: List<Float>
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
        input.forEachIndexed { index, tobacco ->
            Card(
                modifier = Modifier
                    .padding(vertical = MaterialTheme.dimens.extraSmall / 2),
                border = BorderStroke(
                    width = 2.dp,
                    color = setColorTaste(tobacco.taste_group)
                ),
                shape = MaterialTheme.shapes.medium,
                backgroundColor = MaterialTheme.colorScheme.background
            ) {
                Row(modifier = Modifier.basicMarquee()) {
                    Text(
                        modifier = Modifier
                            .padding(
                                horizontal = MaterialTheme.dimens.small1,
                                vertical = MaterialTheme.dimens.extraSmall
                            ),
                        text = "${tobacco.taste}, ${tobacco.brand}",
                        style = MaterialTheme.typography.titleMedium,
                        color = setColorTaste(tobacco.taste_group)
                    )
                    Text(
                        modifier = Modifier.padding(
                            top = MaterialTheme.dimens.extraSmall,
                            bottom = MaterialTheme.dimens.extraSmall,
                            end = MaterialTheme.dimens.small2
                        ),
                        text = "${listTobaccoWeight[index].toInt()} г.",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.outlineVariant
                    )
                }
            }
        }
    }
}
