package com.pidzama.smokecrafthookahapp.presentation.profile

import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.LocaleList
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import com.pidzama.smokecrafthookahapp.R
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.os.LocaleListCompat
import androidx.navigation.NavHostController
import com.pidzama.smokecrafthookahapp.presentation.common.ThemeSwitcher
import com.pidzama.smokecrafthookahapp.presentation.common.bounceClick
import com.pidzama.smokecrafthookahapp.presentation.current_orders.substringToken
import com.pidzama.smokecrafthookahapp.presentation.main.MainViewModel
import com.pidzama.smokecrafthookahapp.ui.theme.dimens


@Composable
fun ProfileScreen(
    viewModel: MainViewModel,
    navController: NavHostController,
    darkTheme: Boolean,
    onThemeUpdated: () -> Unit,
) {

    val openAlertDialog: MutableState<Boolean> = remember { mutableStateOf(false) }

    ChooseLanguageDialog(openAlertDialog)

    Log.d("MyLog", "ALERTDIALOG ${openAlertDialog.value}")
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = MaterialTheme.dimens.small3,
                vertical = MaterialTheme.dimens.small2
            )
    ) {
        item {
            LogoutSection(navController = navController)
            UserDetails(viewModel = viewModel)
            OptionsItemsSection(
                viewModel = viewModel,
                darkTheme = darkTheme,
                onThemeUpdated = onThemeUpdated,
                onClickChangeLanguage = { openAlertDialog.value = true }
            )

        }
    }
}

@Composable
fun LogoutSection(navController: NavHostController) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = MaterialTheme.dimens.nanoSmall),
        horizontalArrangement = Arrangement.End
    ) {
        Button(
            modifier = Modifier
                .bounceClick()
                .height(MaterialTheme.dimens.medium3),
            onClick = { },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            shape = RoundedCornerShape(MaterialTheme.dimens.cornerShape),
        ) {
            Text(
                text = stringResource(id = R.string.logout),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Composable
fun UserDetails(
    viewModel: MainViewModel
) {
    viewModel.getUserLogin()
    val login = viewModel.login.value.substringToken(viewModel.login.value)

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.dimens.small2),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .weight(3f)
                    .size(MaterialTheme.dimens.image)
                    .padding(end = MaterialTheme.dimens.small1)
                    .clip(shape = CircleShape),
                painter = painterResource(id = R.drawable.ic_avatar_user),
                contentDescription = "Your Image",
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.inverseSurface)
            )
            Text(
                modifier = Modifier.weight(6f),
                text = login.uppercase(),
                color = MaterialTheme.colorScheme.inverseSurface,
                style = MaterialTheme.typography.headlineMedium,
                maxLines = 1
            )
            IconButton(
                modifier = Modifier
                    .bounceClick()
                    .weight(weight = 1f, fill = false),
                onClick = { }) {
                Icon(
                    modifier = Modifier
                        .size(MaterialTheme.dimens.medium2),
                    painter = painterResource(id = R.drawable.ic_edit),
                    contentDescription = "edit_profile_icon",
                    tint = MaterialTheme.colorScheme.inverseSurface
                )
            }
        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = MaterialTheme.dimens.small1)
                .height(MaterialTheme.dimens.nanoSmall),
            color = MaterialTheme.colorScheme.primary
        )
    }
}


@Composable
private fun OptionsItemsSection(
    viewModel: MainViewModel,
    darkTheme: Boolean,
    onThemeUpdated: () -> Unit,
    onClickChangeLanguage: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OptionItem(
            icon = R.drawable.profile,
            title = stringResource(id = R.string.account),
            subTitle = stringResource(id = R.string.settings_profile),
            onClick = {})
        OptionItem(
            icon = R.drawable.ic_storage,
            title = stringResource(id = R.string.storage),
            subTitle = stringResource(id = R.string.info_storage),
            onClick = {})
        ThemeSwitcherItem(
            icon = R.drawable.ic_change_theme,
            title = stringResource(id = R.string.theme),
            subTitle = stringResource(id = R.string.change_theme),
            viewModel = viewModel,
            darkTheme = darkTheme,
            onThemeUpdated = onThemeUpdated
        )
        OptionItem(
            icon = R.drawable.ic_language,
            title = stringResource(id = R.string.language),
            subTitle = stringResource(id = R.string.change_language),
            onClick = {
                onClickChangeLanguage()

            }
        )
        OptionItem(
            icon = R.drawable.ic_help,
            title = stringResource(id = R.string.help),
            subTitle = stringResource(id = R.string.faq),
            onClick = {})
    }
}

@Composable
fun ThemeSwitcherItem(
    @DrawableRes icon: Int,
    title: String,
    subTitle: String,
    viewModel: MainViewModel,
    darkTheme: Boolean,
    onThemeUpdated: () -> Unit
) {

    var isDark by remember { mutableStateOf(viewModel.isDarkMode.value) }

    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.dimens.small1),
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.onTertiary
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = MaterialTheme.dimens.nanoSmall
        ),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = MaterialTheme.dimens.nanoSmall),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .weight(1f)
                    .size(MaterialTheme.dimens.medium3),
                painter = painterResource(id = icon),
                contentDescription = title,
                tint = MaterialTheme.colorScheme.inverseSurface
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(weight = 3f, fill = false)
                    .padding(start = MaterialTheme.dimens.small3)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.inverseSurface
                )
                Spacer(modifier = Modifier.height(MaterialTheme.dimens.nanoSmall))
                Text(
                    text = subTitle,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.inverseSurface
                )
            }
            ThemeSwitcher(modifier = Modifier
                .padding(end = MaterialTheme.dimens.small1),
                darkTheme = darkTheme,
                onClick = {
                    isDark = !isDark
                    viewModel.saveThemeMode(isDarkMode = isDark)
                    onThemeUpdated()
                }
            )
        }
    }
}


@Composable
fun OptionItem(
    @DrawableRes icon: Int,
    title: String,
    subTitle: String,
    onClick: () -> Unit
) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.dimens.small1),
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.onTertiary
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = MaterialTheme.dimens.nanoSmall
        ),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .bounceClick { onClick() }
                .padding(bottom = MaterialTheme.dimens.nanoSmall),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .size(MaterialTheme.dimens.medium3),
                painter = painterResource(id = icon),
                contentDescription = title,
                tint = MaterialTheme.colorScheme.inverseSurface
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(weight = 3f, fill = false)
                    .padding(start = MaterialTheme.dimens.small3)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.inverseSurface
                )
                Spacer(modifier = Modifier.height(MaterialTheme.dimens.nanoSmall))
                Text(
                    text = subTitle,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.inverseSurface
                )
            }
            Icon(
                modifier = Modifier
                    .weight(weight = 1f, fill = false),
                painter = painterResource(id = R.drawable.ic_arrow_forward),
                contentDescription = title,
                tint = MaterialTheme.colorScheme.inverseSurface.copy(
                    alpha = 0.6f
                )
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChooseLanguageDialog(
    openDialog: MutableState<Boolean>,
) {
    if (openDialog.value) {
        AlertDialog(onDismissRequest = { openDialog.value = false }) {
            DialogData(openDialog)
        }
    }
}

@Composable
fun DialogData(openDialog: MutableState<Boolean>) {
    val languageList =
        listOf(
            Pair(stringResource(id = R.string.russian), "en"),
            Pair(stringResource(id = R.string.english), "ru")
        )
    var selectedLanguage by remember {
        mutableStateOf(0)
    }
    val context = LocalContext.current
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.padding(
            vertical = MaterialTheme.dimens.small3,
            horizontal = MaterialTheme.dimens.small1
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
                        vertical = MaterialTheme.dimens.small1
                    )
                )
            }
            TextButton(onClick = { openDialog.value = false }) {
                Text(
                    text = stringResource(id = R.string.cancel),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.inverseSurface,
                    modifier = Modifier.padding(vertical = MaterialTheme.dimens.small1)
                )
            }

        }
    }
}


fun changeLanguage(context: Context, localeString: String) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        context.getSystemService(LocaleManager::class.java)
            .applicationLocales = LocaleList.forLanguageTags(localeString)
    } else {
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(localeString))
    }
}
