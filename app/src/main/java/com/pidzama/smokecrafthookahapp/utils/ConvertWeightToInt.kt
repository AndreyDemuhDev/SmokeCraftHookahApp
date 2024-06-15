package com.pidzama.smokecrafthookahapp.utils

fun convertWeightToInt(percentWeight: Double): Int {
    return (Constants.Tasty.bowlVolume * percentWeight / 100).toInt()

}