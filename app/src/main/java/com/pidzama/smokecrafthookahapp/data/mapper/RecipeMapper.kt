package com.pidzama.smokecrafthookahapp.data.mapper

import com.pidzama.smokecrafthookahapp.data.dto.generate_model.ModelRecipeItem
import com.pidzama.smokecrafthookahapp.domain.entities.RecipeModelEntity

interface RecipeMapper {

    fun execute(dto: List<ModelRecipeItem>): List<RecipeModelEntity>
}