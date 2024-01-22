package com.pidzama.smokecrafthookahapp.presentation.on_boarding

import androidx.annotation.DrawableRes
import com.pidzama.smokecrafthookahapp.R

sealed class OnBoardingPages(
    @DrawableRes
    val image: Int,
    val title: String,
    val description: String
) {
    object FirstPage : OnBoardingPages(
        image = R.drawable.ic_launcher_foreground,
        title = "1 экран",
        description = "Описание 1 экрана"
    )

    object SecondPage : OnBoardingPages(
        image = R.drawable.ic_launcher_foreground,
        title = "2 экран",
        description = "Описание 2 экрана"
    )

    object ThirdPage : OnBoardingPages(
        image = R.drawable.ic_launcher_foreground,
        title = "3 экран",
        description = "Описание 3 экрана"
    )
}