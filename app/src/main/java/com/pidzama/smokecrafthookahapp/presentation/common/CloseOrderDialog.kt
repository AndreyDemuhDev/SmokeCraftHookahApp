package com.pidzama.smokecrafthookahapp.presentation.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.pidzama.smokecrafthookahapp.R
import com.pidzama.smokecrafthookahapp.ui.theme.dimens


@Composable
fun CloseOrderAlertDialog(
    showAlert: MutableState<Boolean>,
    action: () -> Unit,
    modifier: Modifier = Modifier
) {

    if (showAlert.value) {
        Dialog(
            onDismissRequest = { showAlert.value = false },
        ) {
            Card(
                shape = RoundedCornerShape(MaterialTheme.dimens.dp_10),
                modifier = modifier
                    .padding(
                        vertical = MaterialTheme.dimens.small1,
                        horizontal = MaterialTheme.dimens.nanoSmall
                    ),
                elevation = CardDefaults.cardElevation(8.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.inverseOnSurface),
                border = BorderStroke(
                    width = MaterialTheme.dimens.dp_1,
                    color = MaterialTheme.colorScheme.primary
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = MaterialTheme.dimens.small3,
                            vertical = MaterialTheme.dimens.small3
                        )

                ) {
                    Text(
                        text = stringResource(id = R.string.close_order),
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = stringResource(id = R.string.sure_you_want_to_close_this_order),
                        color = MaterialTheme.colorScheme.inverseSurface,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        TextButton(onClick = action) {
                            Text(
                                text = stringResource(id = R.string.yes),
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.inverseSurface
                            )
                        }
                        TextButton(onClick = { showAlert.value = false }) {
                            Text(
                                text = stringResource(id = R.string.cancel),
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.inverseSurface,
                            )
                        }
                    }
                }
            }
        }
    }
}