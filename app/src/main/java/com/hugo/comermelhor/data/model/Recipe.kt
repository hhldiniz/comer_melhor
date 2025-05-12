package com.hugo.comermelhor.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class Recipe(
    @PrimaryKey(autoGenerate = true) val recipeId: Int? = null,
    @ColumnInfo val description: String,
    @ColumnInfo val preparation: String,
    @ColumnInfo val calories: Int,
    @ColumnInfo val imageUri: String? = null
)
