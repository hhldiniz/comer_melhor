package com.hugo.comermelhor.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.hugo.comermelhor.data.model.Recipe
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipes")
    fun getRecipes(): Flow<List<Recipe>>

    @Insert
    suspend fun insertRecipe(recipe: Recipe): Long
}