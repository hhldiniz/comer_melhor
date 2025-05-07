package com.hugo.comermelhor.android.widgets

import com.hugo.comermelhor.data.model.Recipe

interface RecipeListHandlers {
    fun onRecipeDescriptionClick(recipe: Recipe)
    fun onRecipeImageClick(recipe: Recipe)
}