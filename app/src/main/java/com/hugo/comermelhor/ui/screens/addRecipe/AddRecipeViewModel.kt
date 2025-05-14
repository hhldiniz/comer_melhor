package com.hugo.comermelhor.ui.screens.addRecipe

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hugo.comermelhor.App
import com.hugo.comermelhor.data.dao.IngredientsDao
import com.hugo.comermelhor.data.dao.RecipeDao
import com.hugo.comermelhor.data.model.Ingredient
import com.hugo.comermelhor.data.model.Recipe
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import androidx.core.net.toUri
import com.hugo.comermelhor.data.services.openfoodapi.OpenFoodApiService

class AddRecipeViewModel(
    private val recipeDao: RecipeDao = App.instance?.db?.recipeDao()!!,
    private val ingredientsDao: IngredientsDao = App.instance?.db?.ingredientDao()!!,
    private val openFoodApiService: OpenFoodApiService? = App.instance?.openFoodApiService
) : ViewModel() {
    private val _uiState = MutableStateFlow(AddRecipeViewState())
    val uiState = _uiState

    fun loadRecipeWithIngredients(recipeId: Int) {
        if (recipeId != -1) {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            viewModelScope.launch {
                recipeDao.getRecipeWithIngredientsById(recipeId).collect {
                    _uiState.value = _uiState.value.copy(
                        recipeId = recipeId,
                        description = it.recipe.description,
                        preparation = it.recipe.preparation,
                        calories = it.recipe.calories,
                        ingredients = it.ingredients,
                        recipeImage = it.recipe.imageUri?.toUri(),
                        isLoading = false, error = null
                    )
                }
            }
        }
    }

    fun updateDescription(description: String) {
        _uiState.value = _uiState.value.copy(description = description)
    }

    fun updatePreparation(preparation: String) {
        _uiState.value = _uiState.value.copy(preparation = preparation)
    }

    fun updateIngredient(ingredient: Ingredient) {
        val ingredientsResult = mutableListOf<Ingredient>()
        val caloriesSearchKeyBuilder = StringBuilder()
        _uiState.value.ingredients.toMutableList().forEach {
            if (it.ingredientId == ingredient.ingredientId) {
                ingredientsResult.add(
                    it.copy(
                        ingredientId = ingredient.ingredientId,
                        name = ingredient.name,
                        recipeId = _uiState.value.recipeId,
                        amount = ingredient.amount,
                        unit = ingredient.unit
                    )
                )
            } else {
                ingredientsResult.add(it)
            }
            ingredientsResult.last().let { ingredient ->
                caloriesSearchKeyBuilder.append("${ingredient.name},")
            }
        }
        viewModelScope.launch {
            flowOf(openFoodApiService?.searchProductByName(caloriesSearchKeyBuilder.toString())).catch {
                Log.e("AddRecipeViewModel", "updateIngredient: ", it)
            }
                .collect { productSearchResponse ->
                    _uiState.value =
                        _uiState.value.copy(calories = productSearchResponse?.products?.sumOf {
                            it.nutriments.energyKcal?.toInt() ?: 0
                        }
                            ?: 0)
                }
        }

        _uiState.value =
            _uiState.value.copy(ingredients = ingredientsResult)
    }

    fun updateImage(imageUri: Uri) {
        _uiState.value = _uiState.value.copy(recipeImage = imageUri)
    }

    fun addIngredient(ingredient: Ingredient) {
        val ingredients = _uiState.value.ingredients.toMutableList()
        ingredients.add(ingredient)

        _uiState.value =
            _uiState.value.copy(ingredients = ingredients)
    }

    fun addRecipeIfNotExists(): Job {
        _uiState.value = _uiState.value.copy(isLoading = true, error = null)
        return viewModelScope.launch {
            val recipe = Recipe(
                recipeId = if (isEditing()) _uiState.value.recipeId else null,
                description = _uiState.value.description,
                preparation = _uiState.value.preparation,
                calories = _uiState.value.calories,
                imageUri = _uiState.value.recipeImage?.toString()
            )
            if (isEditing()) {
                flowOf(recipeDao.updateRecipe(recipe))
            } else {
                flowOf(recipeDao.insertRecipe(recipe)) // INSERT IS NOT WORKING WITH FLOW FOR SOME REASON
            }.catch {
                _uiState.value = _uiState.value.copy(isLoading = false, error = it.message)
            }.collect { recipeId ->
                val ingredients = _uiState.value.ingredients.map {
                    Ingredient(
                        ingredientId = if (isEditing()) it.ingredientId else null,
                        name = it.name,
                        recipeId = if (isEditing()) _uiState.value.recipeId else recipeId.toInt(),
                        amount = it.amount,
                        unit = it.unit
                    )
                }
                flow<Unit> {
                    ingredientsDao.insertIngredients(*ingredients.toTypedArray())
                }.catch { error ->
                    _uiState.value = _uiState.value.copy(isLoading = false, error = error.message)
                }.collect {
                    _uiState.value = _uiState.value.copy(isLoading = false, error = null)
                }
            }
        }
    }

    private fun isEditing(): Boolean {
        return _uiState.value.recipeId != -1
    }

    fun dispatchError(string: String) {
        _uiState.value = _uiState.value.copy(error = string)
    }
}