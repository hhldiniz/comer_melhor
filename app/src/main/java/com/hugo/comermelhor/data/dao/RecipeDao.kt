package com.hugo.comermelhor.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.hugo.comermelhor.data.entities.RecipeWithIngredients
import com.hugo.comermelhor.data.model.Recipe
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipes")
    fun getRecipes(): Flow<List<Recipe>>

    @Insert
    suspend fun insertRecipe(recipe: Recipe): Long

    @Transaction
    @Query("SELECT * FROM recipes WHERE recipes.recipeId = :recipeId")
    fun getRecipeWithIngredientsById(recipeId: Int): Flow<RecipeWithIngredients>

    @Update
    @Transaction
    suspend fun updateRecipe(recipe: Recipe): Int

    @Delete
    suspend fun deleteRecipe(recipe: Recipe): Int
}