package com.hugo.comermelhor.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.hugo.comermelhor.ui.screens.addRecipe.AddRecipeScreen
import com.hugo.comermelhor.ui.screens.recipes.RecipeScreen

@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = Screens.RECIPES.name) {
        composable(Screens.RECIPES.name) {
            RecipeScreen(navController)
        }
        composable(Screens.ADD_RECIPE.name+"/{recipeId}", arguments = listOf(navArgument("recipeId"){
            type = NavType.IntType
            defaultValue = -1
        })) {
            AddRecipeScreen(navController, recipeId = it.arguments?.getInt("recipeId") ?: -1)
        }
    }
}

enum class Screens {
    RECIPES,
    ADD_RECIPE
}