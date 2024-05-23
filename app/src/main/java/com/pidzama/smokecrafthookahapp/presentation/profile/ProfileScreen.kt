package com.pidzama.smokecrafthookahapp.presentation.profile

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import com.pidzama.smokecrafthookahapp.R
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.pidzama.smokecrafthookahapp.presentation.common.ThemeSwitcher
import com.pidzama.smokecrafthookahapp.presentation.common.bounceClick
import com.pidzama.smokecrafthookahapp.presentation.recipe_generation_method.substringToken
import com.pidzama.smokecrafthookahapp.presentation.profile.common.ChooseLanguageDialog
import com.pidzama.smokecrafthookahapp.ui.theme.ScreenOrientation
import com.pidzama.smokecrafthookahapp.ui.theme.dimens


@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    darkTheme: Boolean,
    onThemeUpdated: () -> Unit,
    navController: NavHostController
) {

    val openAlertDialog: MutableState<Boolean> = rememberSaveable() { mutableStateOf(false) }

    ChooseLanguageDialog(openAlertDialog)

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState()),
        color = MaterialTheme.colorScheme.background,
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = MaterialTheme.dimens.small3,
                        end = MaterialTheme.dimens.small3,
                        top = MaterialTheme.dimens.small2,
                        bottom = MaterialTheme.dimens.large1
                    )
            ) {
                LogoutSection(navController=navController)
                UserDetails(viewModel = viewModel)
                OptionsItemsSection(
                    viewModel = viewModel,
                    darkTheme = darkTheme,
                    onThemeUpdated = onThemeUpdated,
                    onClickChangeLanguage = { openAlertDialog.value = true }
                )
            }
        })
}

@Composable
fun LogoutSection(
    navController: NavHostController,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = MaterialTheme.dimens.nanoSmall),
        horizontalArrangement = Arrangement.End
    ) {
        Button(
            modifier = Modifier
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
    viewModel: ProfileViewModel
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
    viewModel: ProfileViewModel,
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
    viewModel: ProfileViewModel,
    darkTheme: Boolean,
    onThemeUpdated: () -> Unit
) {

    var isDark by remember { mutableStateOf(viewModel.isDarkMode.value) }

    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.dimens.small1),
        shape = RoundedCornerShape(MaterialTheme.dimens.dp_10),
        border = BorderStroke(
            width = MaterialTheme.dimens.dp_1,
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
                    .weight(
                        weight = if (ScreenOrientation == Configuration.ORIENTATION_PORTRAIT) 3f else 4f,
                        fill = false
                    )
                    .padding(start = MaterialTheme.dimens.small1)
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
        shape = RoundedCornerShape(MaterialTheme.dimens.dp_10),
        border = BorderStroke(
            width = MaterialTheme.dimens.dp_1,
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
