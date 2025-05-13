package com.hugo.comermelhor.ui.screens.recipes

import android.app.AlertDialog
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.hugo.comermelhor.R
import com.hugo.comermelhor.data.model.Recipe
import com.hugo.comermelhor.ui.navigation.Screens
import com.hugo.comermelhor.ui.util.ContentSharer
import com.hugo.comermelhor.ui.widgets.Error
import com.hugo.comermelhor.ui.widgets.ErrorViewType
import com.hugo.comermelhor.ui.widgets.Loading
import com.hugo.comermelhor.ui.widgets.RecipeList
import com.hugo.comermelhor.ui.widgets.RecipeListHandlers
import kotlinx.coroutines.launch

@Composable
fun RecipeScreen(
    navController: NavController,
    recipesViewModel: RecipesViewModel = viewModel()
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = {
            navController.navigate(Screens.ADD_RECIPE.name + "/-1")
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
                if (state.recipes.isEmpty()) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = stringResource(R.string.no_recipes_found),
                            fontSize = 26.sp,
                            textAlign = TextAlign.Center
                        )
                        Image(
                            modifier = Modifier.size(80.dp),
                            painter = painterResource(R.drawable.nothing_to_show),
                            contentDescription = ""
                        )
                    }
                }
                val galleryLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.GetContent(),
                    onResult = { uri ->
                        uri?.let { resource ->
                            recipesViewModel.updateImageForClickedRecipe(resource)
                        }
                    }
                )
                RecipeList(recipes = state.recipes, onItemClick = object : RecipeListHandlers {
                    override fun onRecipeDescriptionClick(recipe: Recipe) {
                        navController.navigate(Screens.ADD_RECIPE.name + "/${recipe.recipeId}")
                    }

                    override fun onRecipeImageClick(recipe: Recipe) {
                        recipesViewModel.onRecipeClicked(recipe)
                        galleryLauncher.launch("image/*")
                    }

                    override fun onItemDelete(recipe: Recipe) {
                        val dialog = AlertDialog.Builder(context)
                            .setMessage(R.string.recipe_delete_confirmation)
                            .setPositiveButton(R.string.yes_btn_label) { _, _ ->
                                recipesViewModel.deleteRecipe(recipe)
                            }
                            .setNegativeButton(R.string.no_btn_label) { dialog, _ ->
                                dialog.dismiss()
                            }
                        dialog.show()
                    }

                    override fun onShareRecipe(recipe: Recipe) {
                        coroutineScope.launch {
                            recipesViewModel.getIngredientsForRecipe(recipe)
                                .collect { recipeWithIngredients ->
                                    val textToShare = context.getString(
                                        R.string.recipe_share_model,
                                        recipe.description,
                                        recipe.preparation
                                    ) + "\n" + recipeWithIngredients.ingredients.joinToString("\n") { ingredient ->
                                        context.getString(
                                            R.string.ingredients_share_model,
                                            ingredient.amount,
                                            ingredient.unit,
                                            ingredient.name
                                        ) + "\n"
                                    }
                                    ContentSharer.shareText(context, textToShare)
                                }
                        }
                    }
                })
            }
        }
    }
}