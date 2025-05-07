package com.hugo.comermelhor.ui.screens.addRecipe

import com.hugo.comermelhor.data.model.Ingredient

data class AddRecipeViewState(
    val description: String = "",
    val preparation: String = "",
    val ingredients: List<Ingredient> = emptyList(),
    val calories: Int = 0,
    val isLoading: Boolean = false,
    val error: String? = null
)
