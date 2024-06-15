package com.pidzama.smokecrafthookahapp.data.model.orders

data class OrdersItem(
    val created: String,
    val hookah_count: Int,
    val id: Int,
    val is_active: Boolean,
    val order_number: Int,
    val recipes: List<Recipe>,
    val table_number: Int,
    val updated: String
)

data class Recipe(
    val name: String,
    val taste: List<Taste>
)

data class Taste(
    val name: String,
    val weight: Int
)