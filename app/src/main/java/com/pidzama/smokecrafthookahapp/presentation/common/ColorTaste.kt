package com.pidzama.smokecrafthookahapp.presentation.common


import androidx.compose.ui.graphics.Color

enum class ColorTaste(val taste: String) {
    FRUIT(taste = "Фруктовые"),
    BERRY(taste = "Ягодные"),
    CITRUS(taste = "Цитрусовые"),
    ALCOHOLIC(taste = "Алкогольные"),
    SPICY(taste = "Пряные"),
    DESSERT(taste = "Десертные"),
    GRASSY(taste = "Травянистые"),
    WALNUT(taste = "Ореховые"),
    FLORAL(taste = "Цветочные")

}

fun setColorTaste(colorTaste: String): Color {
    return when (colorTaste) {
        ColorTaste.FRUIT.taste -> Color(0xFFEB1B32)
        ColorTaste.BERRY.taste -> Color(0xFF924EFF)
        ColorTaste.CITRUS.taste -> Color(0xFFF4EA00)
        ColorTaste.ALCOHOLIC.taste -> Color(0xFF65FCCF)
        ColorTaste.SPICY.taste -> Color(0xFFEDC894)
        ColorTaste.DESSERT.taste -> Color(0xFFFF6680)
        ColorTaste.GRASSY.taste -> Color(0xFF6CA314)
        ColorTaste.WALNUT.taste -> Color(0xFFBF6614)
        ColorTaste.FLORAL.taste -> Color(0xFFCB7DE1)
        else -> {Color(0xFFFFFFFF)
        }
    }
}