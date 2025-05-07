package com.hugo.comermelhor.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hugo.comermelhor.ui.screens.addRecipe.AddRecipeScreen
import com.hugo.comermelhor.ui.screens.recipes.RecipeScreen

@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = Screens.RECIPES.name) {
        composable(Screens.RECIPES.name) {
            RecipeScreen(navController)
        }
        composable(Screens.ADD_RECIPE.name) {
            AddRecipeScreen(navController)
        }
    }
}

enum class Screens {
    RECIPES,
    ADD_RECIPE
}