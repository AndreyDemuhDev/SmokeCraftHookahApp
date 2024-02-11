package com.pidzama.smokecrafthookahapp.domain.mapper

import com.pidzama.smokecrafthookahapp.data.common.base.Mapper
import com.pidzama.smokecrafthookahapp.data.model.RandomRecipeSubList
import javax.inject.Inject

class RecipeMapper @Inject constructor() :
    Mapper<List<RandomRecipeSubList>, List<RandomRecipeSubList>> {


    override fun fromMap(from: List<RandomRecipeSubList>): List<RandomRecipeSubList> {
        var answer = mutableListOf<RandomRecipeSubList>()
        from.mapIndexed { index, randomRecipeSubLists ->
            answer.add(randomRecipeSubLists)
        }
        return answer
    }
}

