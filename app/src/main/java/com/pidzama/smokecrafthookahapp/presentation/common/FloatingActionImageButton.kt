package com.pidzama.smokecrafthookahapp.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.pidzama.smokecrafthookahapp.R


@Composable
fun FloatingActionImageButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FloatingActionButton(
        onClick = onClick,
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.background,
        modifier = modifier
    ) {
        Image(
            painter = painterResource(id = R.drawable.add_plus),
            contentDescription = stringResource(
                id = R.string.create_new_order
            ),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.background),
            modifier = Modifier.size(26.dp)
        )

    }
}