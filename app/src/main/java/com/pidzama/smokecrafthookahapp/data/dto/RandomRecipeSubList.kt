package com.pidzama.smokecrafthookahapp.data.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Tobaccos")
data class RandomRecipeSubListItem(
    val best_before_date: String,
    val brand: String,
    val created: String,
    @PrimaryKey(autoGenerate = true)
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




