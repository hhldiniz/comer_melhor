package com.hugo.comermelhor.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.hugo.comermelhor.data.model.Ingredient
import kotlinx.coroutines.flow.Flow

@Dao
interface IngredientsDao {
    @Query("SELECT * FROM ingredients WHERE recipeId = :recipeId")
    fun getIngredients(recipeId: Int): Flow<List<Ingredient>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIngredients(vararg ingredients: Ingredient)

    @Update
    suspend fun updateIngredients(vararg ingredient: Ingredient)
}