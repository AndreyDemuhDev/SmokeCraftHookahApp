package com.pidzama.smokecrafthookahapp.utils

fun converterToWeight(percentWeight: Double): String {
    return "${(Constants.Tasty.bowlVolume * percentWeight) / 100} г."
}