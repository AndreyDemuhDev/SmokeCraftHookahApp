package com.pidzama.smokecrafthookahapp.domain.use_case

import com.pidzama.smokecrafthookahapp.data.model.RandomRecipeSubListItem
import com.pidzama.smokecrafthookahapp.domain.repository.RecipeRepository
import javax.inject.Inject

class InsertRecipeToArchiveUseCase @Inject constructor(
    private val repo: RecipeRepository
) {
    suspend fun insertRecipesToDataBase(recipe: List<RandomRecipeSubListItem>){
        repo.insertRecipeToArchive(recipe)
    }
}