package com.hugo.comermelhor.ui.screens.recipes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hugo.comermelhor.App
import com.hugo.comermelhor.data.dao.RecipeDao
import com.hugo.comermelhor.data.model.Recipe
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

class RecipesViewModel(private val recipesDao: RecipeDao = App.instance?.db?.recipeDao()!!) :
    ViewModel() {
    private val _viewState = MutableStateFlow(RecipesViewState())
    val viewState = _viewState

    init {
        getRecipes()
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
}