package com.pidzama.smokecrafthookahapp.presentation.profile.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.pidzama.smokecrafthookahapp.R
import com.pidzama.smokecrafthookahapp.ui.theme.dimens


//кастомный алерт диалог для смены языка приложения
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChooseLanguageDialog(
    openDialog: MutableState<Boolean>,
) {
    if (openDialog.value) {
        AlertDialog(onDismissRequest = { openDialog.value = false }) {
            LanguageDialogStyle(openDialog)
        }
    }
}

@Composable
fun LanguageDialogStyle(openDialog: MutableState<Boolean>) {
    val languageList =
        listOf(
            Pair(stringResource(id = R.string.russian), "ru"),
            Pair(stringResource(id = R.string.english), "en")
        )
    var selectedLanguage by remember {
        mutableStateOf(0)
    }
    val context = LocalContext.current
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.padding(
            vertical = MaterialTheme.dimens.small1,
            horizontal = MaterialTheme.dimens.nanoSmall
        ),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.inverseSurface.copy(alpha = 0.2f))
        ) {
            Column(
                modifier = Modifier
                    .padding(MaterialTheme.dimens.small1)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.choose_language),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
                languageList.forEachIndexed { index, language ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedLanguage == index, onClick = {
                                selectedLanguage = index
                            },
                            enabled = true
                        )
                        Text(
                            text = languageList[index].first,
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            TextButton(onClick = {
                changeLanguage(
                    context,
                    languageList[selectedLanguage].second
                )
                openDialog.value = false
            }) {
                Text(
                    text = stringResource(id = R.string.ok),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.inverseSurface,
                    modifier = Modifier.padding(
                        vertical = MaterialTheme.dimens.extraSmall
                    )
                )
            }
            TextButton(onClick = { openDialog.value = false }) {
                Text(
                    text = stringResource(id = R.string.cancel),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.inverseSurface,
                    modifier = Modifier.padding(vertical = MaterialTheme.dimens.extraSmall)
                )
            }

        }
    }
}
