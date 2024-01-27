package com.pidzama.smokecrafthookahapp.presentation.common

import androidx.annotation.DrawableRes
import com.pidzama.smokecrafthookahapp.R

sealed class BottomBarScreens(
    val route: String,
    val title: String,
    @DrawableRes val  icon: Int
) {
    object Profile : BottomBarScreens(
        route = "PROFILE",
        title = "Мой профиль",
        icon = R.drawable.profile
    )

    object CurrentOrders : BottomBarScreens(
        route = "CURRENT_ORDERS",
        title = "Текущие заказы",
        icon = R.drawable.orders
    )

    object OrderArchive : BottomBarScreens(
        route = "ORDER_ARCHIVE",
        title = "Архив заказов",
        icon = R.drawable.archive
    )
}