package com.pidzama.smokecrafthookahapp.data.dto.orders

import android.os.Parcelable
import com.pidzama.smokecrafthookahapp.domain.entities.RecipeModelEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class OrdersItem(
    val created: String,
    val hookah_count: Int,
    val id: Int,
    val is_active: Boolean,
    val order_number: Int,
    val recipes: List<RecipeModelEntity>,
    val table_number: Int,
    val updated: String
):Parcelable



