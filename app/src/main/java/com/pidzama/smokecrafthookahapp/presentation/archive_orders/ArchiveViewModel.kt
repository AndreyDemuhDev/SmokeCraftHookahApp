package com.pidzama.smokecrafthookahapp.presentation.archive_orders

import com.pidzama.smokecrafthookahapp.data.model.RandomRecipeSubListItem
import com.pidzama.smokecrafthookahapp.domain.use_case.AppUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

//@HiltViewModel
class ArchiveViewModel @Inject constructor(
    private val useCase: AppUseCase
) {

//    val _archiveRecipe = MutableStateFlow(emptyList<RandomRecipeSubListItem>())
    private val _listArchiveRecipes = MutableStateFlow(emptyList<RandomRecipeSubListItem>())
    val listArchiveRecipes : StateFlow<List<RandomRecipeSubListItem>> = _listArchiveRecipes
}