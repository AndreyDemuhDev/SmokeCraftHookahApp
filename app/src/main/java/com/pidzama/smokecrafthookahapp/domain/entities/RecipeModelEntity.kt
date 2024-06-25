package com.pidzama.smokecrafthookahapp.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class RecipeModelEntity(
    val name: String,
    val tasteModel: List<TasteEntity>
) : Parcelable {

    @Parcelize
    data class TasteEntity(
        val idTaste: Int,
        val nameTaste: String,
        val weightTaste: Double,
        val brandTaste: String,
        val groupTaste: String,
    ) : Parcelable
}
