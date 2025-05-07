package com.hugo.comermelhor.android.widgets

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.hugo.comermelhor.data.model.Recipe
import com.hugo.comermelhor.ui.widgets.RecipeCard

@Composable
fun RecipeList(recipes: List<Recipe>, onItemClick: RecipeListHandlers) {
    LazyColumn {
        items(recipes) { recipe ->
            RecipeCard(recipe = recipe, onClick = onItemClick)
        }
    }
}