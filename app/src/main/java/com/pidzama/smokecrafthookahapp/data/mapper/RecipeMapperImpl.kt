package com.pidzama.smokecrafthookahapp.data.mapper

import com.pidzama.smokecrafthookahapp.data.dto.generate_model.ModelRecipeItem
import com.pidzama.smokecrafthookahapp.domain.entities.RecipeModelEntity
import javax.inject.Inject

class RecipeMapperImpl @Inject constructor() : RecipeMapper {


    override fun execute(dto: List<ModelRecipeItem>): List<RecipeModelEntity> {
        return dto.mapIndexed { _, recipe ->
            RecipeModelEntity(
                name = recipe.name,
                tasteModel = recipe.matched_tobaccos.mapIndexed { index, matchedTobacco ->
                    RecipeModelEntity.TasteEntity(
                        nameTaste = recipe.taste[index].name,
                        idTaste = matchedTobacco.id,
                        weightTaste = recipe.taste[index].weight,
                        brandTaste = matchedTobacco.brand,
                        groupTaste = matchedTobacco.taste_group
                    )
                }
            )
        }
    }
}
