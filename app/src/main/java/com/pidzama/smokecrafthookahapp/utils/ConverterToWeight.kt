package com.pidzama.smokecrafthookahapp.utils

fun converterWeightToString(percentWeight: Double): String {
    return "${(Constants.Tasty.bowlVolume * percentWeight.toInt()) / 100} г."
}