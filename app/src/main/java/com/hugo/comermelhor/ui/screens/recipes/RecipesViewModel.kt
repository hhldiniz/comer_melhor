package com.hugo.comermelhor.ui.screens.recipes

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hugo.comermelhor.App
import com.hugo.comermelhor.data.dao.RecipeDao
import com.hugo.comermelhor.data.entities.RecipeWithIngredients
import com.hugo.comermelhor.data.model.Recipe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

class RecipesViewModel(private val recipesDao: RecipeDao = App.instance?.db?.recipeDao()!!) :
    ViewModel() {
    private val _viewState = MutableStateFlow(RecipesViewState())
    val viewState = _viewState
    private var _lastClickedRecipe: Recipe? = null

    init {
        getRecipes()
    }

    //Only used when clicked on a recipe image because it has to be accessed in a Composable context later on
    fun onRecipeClicked(recipe: Recipe) {
        _lastClickedRecipe = recipe
    }

    fun updateImageForClickedRecipe(uri: Uri) {
        val recipeToUpdate = _lastClickedRecipe?.copy(imageUri = uri.toString())!!
        _viewState.value = _viewState.value.copy(isLoading = true, error = null)
        viewModelScope.launch {
            flowOf(recipesDao.updateRecipe(recipeToUpdate)).catch { error ->
                _viewState.value = _viewState.value.copy(isLoading = false, error = error.message)
            }.collect {
                _viewState.value = _viewState.value.copy(isLoading = false, error = null)
                _viewState.value =
                    _viewState.value.copy(recipes = _viewState.value.recipes.map { if (it.recipeId == recipeToUpdate.recipeId) recipeToUpdate else it }
                        .toMutableList())
            }
        }
    }

    fun getRecipes() {
        _viewState.value = _viewState.value.copy(isLoading = true, error = null)
        viewModelScope.launch {
            recipesDao.getRecipes().catch {
                _viewState.value = _viewState.value.copy(isLoading = false, error = it.message)
            }.collect {
                _viewState.value =
                    _viewState.value.copy(recipes = it, isLoading = false, error = null)
            }
        }
    }

    fun deleteRecipe(recipe: Recipe) {
        _viewState.value = _viewState.value.copy(isLoading = true, error = null)
        viewModelScope.launch {
            flowOf(recipesDao.deleteRecipe(recipe)).catch {
                _viewState.value = _viewState.value.copy(isLoading = false, error = it.message)
            }.collect {
                _viewState.value =
                    _viewState.value.copy(recipes = _viewState.value.recipes.toMutableList().apply {
                        remove(recipe)
                    }, isLoading = false, error = null)
            }
        }
    }

    fun getIngredientsForRecipe(recipe: Recipe): Flow<RecipeWithIngredients> {
        return recipesDao.getRecipeWithIngredientsById(recipe.recipeId ?: 0)
    }

    fun dispatchError(error: String) {
        _viewState.value = _viewState.value.copy(error = error)
    }
}