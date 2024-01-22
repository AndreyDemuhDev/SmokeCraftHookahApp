package com.pidzama.smokecrafthookahapp.data.model

import androidx.compose.ui.graphics.Color

data class Recipe(
    val id: Int,
    val tabaccos: List<Tabacco>,
)

data class Tabacco(
    val name: String,
    val creater: String,
    val value: Int,
    val color: Color,
    val isTapped: Boolean = false
)
