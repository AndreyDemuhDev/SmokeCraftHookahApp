package com.pidzama.smokecrafthookahapp.domain.use_case

class AppUseCase(
    val login : LoginUseCase,
    val recipes: RecipesUseCase,
    val getAllRecipesInDataBaseUseCase: GetAllRecipesInDataBaseUseCase,
    val insertRecipeToArchiveUseCase: InsertRecipeToArchiveUseCase,
    val reduceRecipeUseCase: ReduceRecipeUseCase
)