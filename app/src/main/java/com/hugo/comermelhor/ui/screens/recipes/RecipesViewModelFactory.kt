package com.hugo.comermelhor.ui.screens.recipes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.hugo.comermelhor.App
import kotlin.reflect.KClass

class RecipesViewModelFactory(private val application: App): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: KClass<T>, extras: CreationExtras): T {
        @Suppress("UNCHECKED_CAST")
        return RecipesViewModel(application.db?.recipeDao()!!) as T
    }
}