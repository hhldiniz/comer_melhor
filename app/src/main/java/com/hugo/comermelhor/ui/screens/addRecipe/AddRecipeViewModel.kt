package com.hugo.comermelhor.ui.screens.addRecipe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hugo.comermelhor.App
import com.hugo.comermelhor.data.dao.IngredientsDao
import com.hugo.comermelhor.data.dao.RecipeDao
import com.hugo.comermelhor.data.entities.RecipeWithIngredients
import com.hugo.comermelhor.data.model.Ingredient
import com.hugo.comermelhor.data.model.Recipe
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

class AddRecipeViewModel(
    private val recipeDao: RecipeDao = App.instance?.db?.recipeDao()!!,
    private val ingredientsDao: IngredientsDao = App.instance?.db?.ingredientDao()!!
) : ViewModel() {
    private val _uiState = MutableStateFlow(AddRecipeViewState())
    val uiState = _uiState

    fun editRecipe(recipeWithIngredients: RecipeWithIngredients) {
        _uiState.value = _uiState.value.copy(
            description = recipeWithIngredients.recipe.description,
            preparation = recipeWithIngredients.recipe.preparation,
            ingredients = recipeWithIngredients.ingredients,
            calories = recipeWithIngredients.recipe.calories
        )
    }

    fun updateDescription(description: String) {
        _uiState.value = _uiState.value.copy(description = description)
    }

    fun updatePreparation(preparation: String) {
        _uiState.value = _uiState.value.copy(preparation = preparation)
    }

    fun updateIngredient(ingredient: Ingredient) {
        val ingredients = _uiState.value.ingredients.toMutableList()
        ingredients.removeIf { it.ingredientId == ingredient.ingredientId }
        ingredients.add(ingredient)

        _uiState.value =
            _uiState.value.copy(ingredients = ingredients)
    }

    fun addIngredient(ingredient: Ingredient) {
        val ingredients = _uiState.value.ingredients.toMutableList()
        ingredients.add(ingredient)

        _uiState.value =
            _uiState.value.copy(ingredients = ingredients)
    }

    fun addRecipe() {
        _uiState.value = _uiState.value.copy(isLoading = true, error = null)
        viewModelScope.launch {
            flowOf(
                recipeDao.insertRecipe(
                    Recipe(
                        description = _uiState.value.description,
                        preparation = _uiState.value.preparation,
                        calories = _uiState.value.calories
                    )
                )
            ).catch {
                _uiState.value = _uiState.value.copy(isLoading = false, error = it.message)
            }.collect { recipeId ->
                ingredientsDao.insertIngredients(
                    *_uiState.value.ingredients.map {
                        Ingredient(
                            name = it.name,
                            recipeId = recipeId.toInt(),
                            amount = it.amount,
                            unit = it.unit
                        )
                    }.toTypedArray()
                )
                _uiState.value = _uiState.value.copy(isLoading = false, error = null)
            }
        }
    }
}