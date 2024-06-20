package com.pidzama.smokecrafthookahapp.data.remote.order

import com.pidzama.smokecrafthookahapp.data.model.generate_model.ModelRecipeItem

data class OrderRequest(
    val is_active:Boolean =  true,
    val table_number: Int,
    val hookah_count: Int,
    val recipes: List<ModelRecipeItem>
)