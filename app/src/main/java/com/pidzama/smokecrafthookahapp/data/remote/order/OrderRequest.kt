package com.pidzama.smokecrafthookahapp.data.remote.order

import com.pidzama.smokecrafthookahapp.domain.entities.RecipeModelEntity


data class OrderRequest(
    val is_active:Boolean =  true,
    val table_number: Int,
    val hookah_count: Int,
    val recipes: List<RecipeModelEntity>
)