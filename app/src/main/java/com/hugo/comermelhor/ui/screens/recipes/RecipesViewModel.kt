package com.hugo.comermelhor.ui.screens.recipes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hugo.comermelhor.App
import com.hugo.comermelhor.data.dao.RecipeDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
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
}