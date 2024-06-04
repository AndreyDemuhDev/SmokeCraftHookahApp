package com.pidzama.smokecrafthookahapp.presentation.detail_hookah

//import com.pidzama.smokecrafthookahapp.data.repository.SmokeCraftRepository
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pidzama.smokecrafthookahapp.data.model.RandomRecipeSubListItem
import com.pidzama.smokecrafthookahapp.data.remote.reduce.ReduceRecipeResponse
import com.pidzama.smokecrafthookahapp.domain.use_case.AppUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailHookahViewModel @Inject constructor(
    private val useCase: AppUseCase,
) : ViewModel() {

    private val _resultRecipeState: MutableState<RecipeState> = mutableStateOf(RecipeState())
    val res: State<RecipeState> = _resultRecipeState

    private val _listArchiveTobaccos = MutableStateFlow(emptyList<RandomRecipeSubListItem>())
    val listArchiveTobaccos :StateFlow<List<RandomRecipeSubListItem>> = _listArchiveTobaccos


//    fun reduceRecipe(recipe: NewRecipeItem) {
//        viewModelScope.launch {
//            useCase.reduceRecipeUseCase.reduceRecipe(recipe)
//                .doOnSuccess {
//                    _resultRecipeState.value = RecipeState(
//                        data = it
//                    )
//                }.doOnFailure {
//                    _resultRecipeState.value = RecipeState(
//                        error = it.message!!
//                    )
//                }.collect()
//        }
//    }

    init {
        viewModelScope.launch {
            _listArchiveTobaccos.emit(useCase.getAllRecipesInDataBaseUseCase.getAllRecipesInDataBaseUseCase())
        }

    }

//    fun insertRecipes(recipe: List<NewRecipeItem>){
//        viewModelScope.launch {
//           useCase.insertRecipeToArchiveUseCase.insertRecipesToDataBase(recipe)
//            _listArchiveTobaccos.emit(useCase.getAllRecipesInDataBaseUseCase.getAllRecipesInDataBaseUseCase())
//        }
//    }

}

data class RecipeState(
    val data: ReduceRecipeResponse? = null,
    val error: String = ""
)