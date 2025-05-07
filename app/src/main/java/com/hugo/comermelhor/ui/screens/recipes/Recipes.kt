package com.hugo.comermelhor.ui.screens.recipes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.hugo.comermelhor.R
import com.hugo.comermelhor.ui.widgets.RecipeList
import com.hugo.comermelhor.android.widgets.RecipeListHandlers
import com.hugo.comermelhor.data.model.Recipe
import com.hugo.comermelhor.ui.navigation.Screens
import com.hugo.comermelhor.ui.widgets.Error
import com.hugo.comermelhor.ui.widgets.ErrorViewType
import com.hugo.comermelhor.ui.widgets.Loading

@Composable
fun RecipeScreen(
    navController: NavController,
    recipesViewModel: RecipesViewModel = viewModel()
) {
    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = {
            navController.navigate(Screens.ADD_RECIPE.name)
        }) {
            Icon(Icons.Default.Add, contentDescription = "Add")
        }
    }) {
        Surface(modifier = Modifier.padding(it)) {
            val state by recipesViewModel.viewState.collectAsState()
            if (state.isLoading) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Loading(Modifier.size(100.dp))
                }
            }
            if (state.error != null) {
                Error(
                    errorViewType = ErrorViewType.Retry,
                    message = stringResource(R.string.loading_recipes_error_msg),
                    retryBtnListener = {
                        recipesViewModel.getRecipes()
                    }
                )
            }
            if (!state.isLoading && state.error == null) {
                RecipeList(recipes = state.recipes, onItemClick = object : RecipeListHandlers {
                    override fun onRecipeDescriptionClick(recipe: Recipe) {

                    }

                    override fun onRecipeImageClick(recipe: Recipe) {

                    }
                })
            }
        }
    }
}