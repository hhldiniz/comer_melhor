package com.hugo.comermelhor.ui.screens.recipes

import com.hugo.comermelhor.data.model.Recipe

data class RecipesViewState(
    val recipes: List<Recipe> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)