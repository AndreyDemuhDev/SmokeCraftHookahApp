package com.pidzama.smokecrafthookahapp.presentation.profile

import android.media.Image
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import com.pidzama.smokecrafthookahapp.R
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.pidzama.smokecrafthookahapp.presentation.common.ThemeSwitcher
import com.pidzama.smokecrafthookahapp.presentation.main.MainViewModel
import com.pidzama.smokecrafthookahapp.ui.theme.dimens
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun ProfileScreen(
    onBoardingViewModel: MainViewModel,
    darkTheme: Boolean, onThemeUpdated: () -> Unit
) {
    Text(text = "Profile Screen", color = MaterialTheme.colors.primary)
    var isDark by remember {
        mutableStateOf(onBoardingViewModel.isDarkMode.value)
    }

    var listPrepared by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        withContext(Dispatchers.Default) {
            optionsList.clear()

            // Add the data to optionsList
            prepareOptionsData()

            listPrepared = true
        }
    }

    if (listPrepared) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {

            item {
                ThemeSwitcher(
                    darkTheme = darkTheme,
                    onClick = {
                        isDark = !isDark
                        onBoardingViewModel.saveThemeMode(isDarkMode = isDark)
                        onThemeUpdated()
                    }
                )
                UserDetails()
            }
            items(optionsList) { item ->
                OptionsItemStyle(item = item)
            }

        }
    }
}

@Composable
fun UserDetails() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(androidx.compose.material3.MaterialTheme.dimens.small3),
        verticalAlignment = Alignment.CenterVertically
    ) {

        // User's image

        Image(
            modifier = Modifier
                .size(androidx.compose.material3.MaterialTheme.dimens.image)
                .clip(shape = CircleShape),
            painter = painterResource(id = R.drawable.ic_apply),
            contentDescription = "Your Image"
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(weight = 3f, fill = false)
                    .padding(start = androidx.compose.material3.MaterialTheme.dimens.small3)
            ) {

                // User's name
                Text(
                    text = "Victoria Steele",
                    style = androidx.compose.material3.MaterialTheme.typography.labelMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(androidx.compose.material3.MaterialTheme.dimens.extraSmall))

                // User's email
                Text(
                    text = "email@email.com",
                    style = androidx.compose.material3.MaterialTheme.typography.labelSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // Edit button
            IconButton(
                modifier = Modifier
                    .weight(weight = 1f, fill = false),
                onClick = {

                }) {
                Icon(
                    modifier = Modifier.size(androidx.compose.material3.MaterialTheme.dimens.medium2),
                    imageVector = Icons.Outlined.Edit,
                    contentDescription = "Edit Details",
                    tint = MaterialTheme.colors.primary
                )
            }

        }
    }
}

@Composable
private fun OptionsItemStyle(item: OptionsData) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = true) {

            }
            .padding(all = androidx.compose.material3.MaterialTheme.dimens.small3),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(androidx.compose.material3.MaterialTheme.dimens.small2)
                .background(Color.Red),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(containerColor = androidx.compose.material3.MaterialTheme.colorScheme.primary)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .size(androidx.compose.material3.MaterialTheme.dimens.medium3),
                    imageVector = item.icon,
                    contentDescription = item.title,
                    tint = MaterialTheme.colors.primary
                )
                Column(
                    modifier = Modifier
                        .weight(weight = 3f, fill = false)
                        .padding(start = androidx.compose.material3.MaterialTheme.dimens.small3)
                ) {

                    Text(
                        text = item.title,
                        style = androidx.compose.material3.MaterialTheme.typography.headlineMedium,
                    )
                    Spacer(modifier = Modifier.height(androidx.compose.material3.MaterialTheme.dimens.extraSmall))
                    Text(
                        text = item.subTitle,
                        style = androidx.compose.material3.MaterialTheme.typography.titleLarge,
                    )
                }
                Icon(
                    modifier = Modifier
                        .weight(weight = 1f, fill = false),
                    imageVector = Icons.Outlined.Check,
                    contentDescription = item.title,
                    tint = Color.Black.copy(alpha = 0.70f)
                )
            }
        }
    }
}

private val optionsList: ArrayList<OptionsData> = ArrayList()

private fun prepareOptionsData() {

    val appIcons = Icons.Outlined

    optionsList.add(
        OptionsData(
            icon = appIcons.Person,
            title = "Account",
            subTitle = "Manage your account"
        )
    )

    optionsList.add(
        OptionsData(
            icon = appIcons.ShoppingCart,
            title = "Orders",
            subTitle = "Orders history"
        )
    )

    optionsList.add(
        OptionsData(
            icon = appIcons.Person,
            title = "Addresses",
            subTitle = "Your saved addresses"
        )
    )

    optionsList.add(
        OptionsData(
            icon = appIcons.Refresh,
            title = "Saved Cards",
            subTitle = "Your saved debit/credit cards"
        )
    )

    optionsList.add(
        OptionsData(
            icon = appIcons.Settings,
            title = "Settings",
            subTitle = "App notification settings"
        )
    )

    optionsList.add(
        OptionsData(
            icon = appIcons.Check,
            title = "Help Center",
            subTitle = "FAQs and customer support"
        )
    )
}

data class OptionsData(val icon: ImageVector, val title: String, val subTitle: String)