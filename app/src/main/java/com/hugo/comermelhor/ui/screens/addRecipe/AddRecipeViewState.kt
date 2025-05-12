package com.hugo.comermelhor.ui.screens.addRecipe

import android.net.Uri
import com.hugo.comermelhor.data.model.Ingredient

data class AddRecipeViewState(
    val recipeId: Int = -1,
    val recipeImage: Uri? = null,
    val description: String = "",
    val preparation: String = "",
    val ingredients: List<Ingredient> = emptyList(),
    val calories: Int = 0,
    val isLoading: Boolean = false,
    val error: String? = null
)
