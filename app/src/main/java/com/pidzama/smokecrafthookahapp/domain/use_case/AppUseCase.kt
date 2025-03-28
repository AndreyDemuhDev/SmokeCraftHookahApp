package com.pidzama.smokecrafthookahapp.domain.use_case

class AppUseCase(
    val login: LoginUseCase,
    val recipes: RecipesUseCase,
//    val getAllTobaccosList: GetAllTobaccosListUseCase,
    val getAllRecipesInDataBaseUseCase: GetAllRecipesInDataBaseUseCase,
    val insertRecipeToArchiveUseCase: InsertRecipeToArchiveUseCase,
    val reduceRecipeUseCase: ReduceRecipeUseCase,
    val ordersList: OrdersListUseCase,
    val createOrder: CreateOrderUseCase,
    val getInfoOrder: OrderInfoUseCase,
    val deleteOrder: DeleteOrderUseCase,
)