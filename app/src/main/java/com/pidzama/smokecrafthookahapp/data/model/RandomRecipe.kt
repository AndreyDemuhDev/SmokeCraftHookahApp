package com.pidzama.smokecrafthookahapp.data.model

import com.google.gson.annotations.SerializedName


data class RandomRecipe(
    val listRandomRecipe: List<NewRandomRecipeSubList> = emptyList()
)

data class NewRandomRecipeSubList(
    val listTobacco: List<TobaccoItem> = emptyList()
)



data class TobaccoItem(
    val best_before_date: String,
    val brand: String,
    val created: String,
    @SerializedName("tobacco_id")
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
)








