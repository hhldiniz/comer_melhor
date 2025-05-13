package com.hugo.comermelhor.ui.widgets

import com.hugo.comermelhor.data.model.Recipe

interface RecipeListHandlers {
    fun onRecipeDescriptionClick(recipe: Recipe)
    fun onRecipeImageClick(recipe: Recipe)
    fun onItemDelete(recipe: Recipe)
    fun onShareRecipe(recipe: Recipe)
}