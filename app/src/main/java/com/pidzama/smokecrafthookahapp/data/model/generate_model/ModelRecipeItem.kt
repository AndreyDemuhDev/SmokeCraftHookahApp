package com.pidzama.smokecrafthookahapp.data.model.generate_model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ModelRecipeItem(
    val matched_tobaccos: List<MatchedTobacco>,
    val name: String,
    val taste: List<Taste>
) : Parcelable

@Parcelize
data class Taste(
    val name: String,
    val weight: Double
) : Parcelable

@Parcelize
data class MatchedTobacco(
    val brand: String,
    val created: String,
    val id: Int,
    val is_active: Boolean,
    val organization: Int,
    val taste: String,
    val taste_group: String,
    val updated: String,
    val weight: Int
) : Parcelable

