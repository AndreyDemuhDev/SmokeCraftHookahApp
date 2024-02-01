package com.pidzama.smokecrafthookahapp.data.model

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

@Parcelize
data class RandomRecipeSubListItem(
    val best_before_date: String,
    val brand: String,
    val created: String,
    val id: Int,
    val is_active: Boolean,
    val organization_id: Int,
    val price: Double,
    val purchase_date: String,
    val supplier: String,
    val taste: String,
    val taste_group: String,
    val updated: String,
    val weight: Double
): Parcelable


