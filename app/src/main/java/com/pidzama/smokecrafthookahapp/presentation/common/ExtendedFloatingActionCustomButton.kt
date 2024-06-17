package com.pidzama.smokecrafthookahapp.presentation.common


import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource

@Composable
fun ExtendedFloatingActionCustomButton(
    @DrawableRes icon: Int,
    @StringRes contentDescription: Int,
    onClick: () -> Unit,
    textColor: Color,
    backgroundColor: Color,
    contentColor: Color = textColor,
) {
    ExtendedFloatingActionButton(
        icon = {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = stringResource(id = contentDescription)
            )
        },

        shape = MaterialTheme.shapes.extraLarge,
        contentColor = contentColor,
        containerColor = backgroundColor,
        text = {
            Text(
                stringResource(id = contentDescription),
                color = textColor,
                style = MaterialTheme.typography.titleLarge
            )
        },
        onClick = onClick
    )
}