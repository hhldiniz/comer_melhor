package com.hugo.comermelhor.data.entities

import androidx.room.Embedded
import androidx.room.Relation
import com.hugo.comermelhor.data.model.Ingredient
import com.hugo.comermelhor.data.model.Recipe

data class RecipeWithIngredients(
    @Embedded
    val recipe: Recipe,
    @Relation(parentColumn = "recipeId", entityColumn = "recipeId", entity = Ingredient::class)
    val ingredients: List<Ingredient>
)
