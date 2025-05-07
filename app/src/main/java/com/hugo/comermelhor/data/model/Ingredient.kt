package com.hugo.comermelhor.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "ingredients", foreignKeys = [ForeignKey(
        entity = Recipe::class,
        parentColumns = ["recipeId"],
        childColumns = ["recipeId"],
        onDelete = ForeignKey.NO_ACTION
    )]
)
data class Ingredient(
    @PrimaryKey(autoGenerate = true) val ingredientId: Int = 0,
    val name: String,
    val recipeId: Int,
    val amount: Float,
    val unit: String
)