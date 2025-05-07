package com.hugo.comermelhor.ui.widgets

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hugo.comermelhor.android.widgets.RecipeListHandlers
import com.hugo.comermelhor.data.model.Recipe

@Composable
fun RecipeList(modifier: Modifier = Modifier, recipes: List<Recipe>, onItemClick: RecipeListHandlers) {
    LazyColumn(modifier = modifier) {
        items(recipes) { recipe ->
            RecipeCard(modifier = Modifier.padding(16.dp), recipe = recipe, onClick = onItemClick)
        }
    }
}