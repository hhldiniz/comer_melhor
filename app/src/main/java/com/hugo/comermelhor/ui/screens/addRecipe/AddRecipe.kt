package com.hugo.comermelhor.ui.screens.addRecipe

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.hugo.comermelhor.R
import com.hugo.comermelhor.data.model.Ingredient
import com.hugo.comermelhor.ui.widgets.Error
import com.hugo.comermelhor.ui.widgets.ErrorViewType
import com.hugo.comermelhor.ui.widgets.Loading
import com.hugo.comermelhor.ui.widgets.PrimaryButton

@Composable
fun AddRecipeScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    addRecipeViewModel: AddRecipeViewModel = viewModel()
) {
    val state by addRecipeViewModel.uiState.collectAsState()
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        if (state.isLoading) {
            Loading()
        } else {
            if (state.error != null) {
                Error(
                    errorViewType = ErrorViewType.Retry,
                    message = stringResource(R.string.add_recipe_error_msg)
                )
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(
                            Modifier
                                .padding(8.dp)
                        ) {
                            OutlinedTextField(
                                state.description,
                                label = { Text(stringResource(R.string.description_field_label)) },
                                modifier = Modifier.fillMaxWidth(),
                                onValueChange = addRecipeViewModel::updateDescription
                            )
                            OutlinedTextField(
                                state.preparation,
                                singleLine = false,
                                label = { Text(stringResource(R.string.preparation_field_label)) },
                                modifier = Modifier.fillMaxWidth().height(200.dp),
                                onValueChange = addRecipeViewModel::updatePreparation
                            )
                        }
                    }

                    IngredientsSection(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(400.dp)
                            .padding(top = 16.dp),
                        state = state,
                        addRecipeViewModel = addRecipeViewModel
                    )
                }

                Column {
                    Text(stringResource(R.string.calories, state.calories))

                    PrimaryButton(
                        text = stringResource(R.string.add_recipe_btn),
                        modifier = Modifier.padding(bottom = 16.dp)
                    ) {
                        addRecipeViewModel.addRecipe()
                    }
                }

            }
        }

    }
}

@Composable
private fun IngredientsSection(
    modifier: Modifier = Modifier,
    state: AddRecipeViewState,
    addRecipeViewModel: AddRecipeViewModel
) {
    Card(modifier = modifier, elevation = CardDefaults.cardElevation(8.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .align(Alignment.CenterVertically),
                text = stringResource(R.string.ingredients_section_title)
            )
            IconButton(onClick = {
                addRecipeViewModel.addIngredient(
                    Ingredient(
                        ingredientId = state.ingredients.count() + 1,
                        name = "",
                        recipeId = 0,
                        amount = 0f,
                        unit = ""
                    )
                )
            }) {
                Icon(Icons.Filled.Add, contentDescription = "")
            }
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(state.ingredients) { ingredient ->
                Row {
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(0.6f).padding(horizontal = 4.dp),
                        value = ingredient.name,
                        label = { Text(stringResource(R.string.ingredient_description_label)) },
                        onValueChange = { addRecipeViewModel.updateIngredient(ingredient.copy(name = it)) }
                    )
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(0.45f).padding(horizontal = 4.dp),
                        value = ingredient.amount.toString(),
                        label = { Text(stringResource(R.string.ingredient_amount_description_label)) },
                        onValueChange = { addRecipeViewModel.updateIngredient(ingredient.copy(amount = it.toFloat())) }
                    )
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(1f).padding(horizontal = 4.dp),
                        value = ingredient.unit,
                        label = { Text(stringResource(R.string.ingredient_unit_description_label)) },
                        onValueChange = { addRecipeViewModel.updateIngredient(ingredient.copy(unit = it)) }
                    )
                }
            }
        }
    }
}