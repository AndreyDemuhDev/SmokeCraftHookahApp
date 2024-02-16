package com.pidzama.smokecrafthookahapp.domain.use_case

import com.pidzama.smokecrafthookahapp.data.model.RandomRecipeSubListItem
import com.pidzama.smokecrafthookahapp.domain.repository.RecipeRepository
import javax.inject.Inject

class GetAllRecipesInDataBaseUseCase @Inject constructor(
    private val repo: RecipeRepository
) {
    suspend fun getAllRecipesInDataBaseUseCase() : List<RandomRecipeSubListItem>{
       return repo.getListArchiveRecipes()
    }

}
