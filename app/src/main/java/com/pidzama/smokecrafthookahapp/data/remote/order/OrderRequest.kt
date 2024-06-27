package com.pidzama.smokecrafthookahapp.data.remote.order

import com.google.gson.annotations.SerializedName
import com.pidzama.smokecrafthookahapp.domain.entities.RecipeModelEntity


data class OrderRequest(
    @SerializedName("is_active")
    val is_active:Boolean =  true,
    @SerializedName("table_number")
    val table_number: Int,
    @SerializedName("hookah_count")
    val hookah_count: Int,
    @SerializedName("recipes")
    val recipes: List<RecipeModelEntity>
)