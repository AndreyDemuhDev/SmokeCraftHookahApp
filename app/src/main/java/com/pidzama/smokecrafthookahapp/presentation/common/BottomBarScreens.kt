package com.pidzama.smokecrafthookahapp.presentation.common

import androidx.annotation.DrawableRes
import com.pidzama.smokecrafthookahapp.R

sealed class BottomBarScreens(
    val route: String,
    val title: String,
    @DrawableRes val  icon: Int
) {
    object Home : BottomBarScreens(
        route = "HOME",
        title = "Мой профиль",
        icon = R.drawable.profile
    )

    object Favorite : BottomBarScreens(
        route = "FAVORITE",
        title = "Текущие заказы",
        icon = R.drawable.orders
    )

    object Settings : BottomBarScreens(
        route = "SETTINGS",
        title = "Архив заказов",
        icon = R.drawable.archive
    )
}