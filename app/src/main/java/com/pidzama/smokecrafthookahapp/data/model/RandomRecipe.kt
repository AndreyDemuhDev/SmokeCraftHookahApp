package com.pidzama.smokecrafthookahapp.data.model

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json


data class RandomRecipe(
    val listRandomRecipe: List<List<RandomRecipeSubList>>
)

data class NewRandomRecipeSubList(
    val listTobacco: List<TobaccoItem> = emptyList()
)



data class Example(
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

class TobaccoItem {
    @Json(name = "id")
    var id: Int? = null

    @Json(name = "is_active")
    var isActive: Boolean? = null

    @Json(name = "created")
    var created: String? = null

    @Json(name = "updated")
    var updated: String? = null

    @Json(name = "organization_id")
    var organizationId: Int? = null

    @Json(name = "brand")
    var brand: String? = null

    @Json(name = "supplier")
    var supplier: String? = null

    @Json(name = "taste")
    var taste: String? = null

    @Json(name = "taste_group")
    var tasteGroup: String? = null

    @Json(name = "purchase_date")
    var purchaseDate: String? = null

    @Json(name = "best_before_date")
    var bestBeforeDate: String? = null

    @Json(name = "price")
    var price: Double? = null

    @Json(name = "weight")
    var weight: Double? = null
}








