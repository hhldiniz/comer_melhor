package com.hugo.comermelhor.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hugo.comermelhor.data.dao.IngredientsDao
import com.hugo.comermelhor.data.dao.RecipeDao
import com.hugo.comermelhor.data.model.Ingredient
import com.hugo.comermelhor.data.model.Recipe

@Database(entities = [Recipe::class, Ingredient::class], version = 1)
abstract class AppDatabase:  RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
    abstract fun ingredientDao(): IngredientsDao
}