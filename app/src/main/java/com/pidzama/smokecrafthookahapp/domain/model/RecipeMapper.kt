package com.pidzama.smokecrafthookahapp.domain.model

import com.pidzama.smokecrafthookahapp.data.common.base.Mapper
import com.pidzama.smokecrafthookahapp.data.model.generate_model.ModelRecipeItem
import javax.inject.Inject

class RecipeMapper @Inject constructor() :
    Mapper<List<ModelRecipeItem>, List<ModelRecipeItem>> {


    override fun fromMap(from: List<ModelRecipeItem>): List<ModelRecipeItem> {
        val answer = mutableListOf<ModelRecipeItem>()
        from.mapIndexed { index, randomRecipeSubLists ->
            answer.add(randomRecipeSubLists)
        }
        return answer
    }
}

