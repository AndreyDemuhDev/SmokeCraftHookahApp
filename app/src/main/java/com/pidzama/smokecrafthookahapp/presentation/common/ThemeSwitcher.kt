package com.pidzama.smokecrafthookahapp.presentation.common

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.res.painterResource
import com.pidzama.smokecrafthookahapp.R
import com.pidzama.smokecrafthookahapp.ui.theme.dimens

@Composable
fun ThemeSwitcher(
    modifier: Modifier,
    darkTheme: Boolean,
    size: Dp = MaterialTheme.dimens.medium3,
    padding: Dp = MaterialTheme.dimens.extraSmall,
    borderWidth: Dp = MaterialTheme.dimens.dp_1,
    parentShape: Shape = CircleShape,
    toggleShape: Shape = CircleShape,
    animationSpec: AnimationSpec<Dp> = tween(durationMillis = 500),
    onClick: () -> Unit
) {

    val offset by animateDpAsState(
        targetValue = if (darkTheme) 0.dp else size,
        animationSpec = animationSpec
    )

    Box(modifier = modifier
        .width(size * 2)
        .height(size)
        .clip(shape = parentShape)
        .border(
            border = BorderStroke(
                width = borderWidth,
                color = MaterialTheme.colorScheme.inverseSurface
            ),
            shape = parentShape
        )
        .clickable { onClick() }
        .background(MaterialTheme.colorScheme.onTertiary)
    ) {
        Box(
            modifier = Modifier
                .size(size)
                .offset(x = offset)
                .padding(all = padding)
                .clip(shape = toggleShape)
                .background(MaterialTheme.colorScheme.inverseSurface)
        ) {}
    }
}