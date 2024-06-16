package com.pidzama.smokecrafthookahapp.data.remote.order

import com.pidzama.smokecrafthookahapp.data.model.generate_model.Taste

data class OrderResponse(
    val id: Int,
    val is_active: Boolean,
    val created: String,
    val updated: String,
    val order_number: Int,
    val table_number: Int,
    val hookah_count: Int,
    val recipes: List<Taste>,
)