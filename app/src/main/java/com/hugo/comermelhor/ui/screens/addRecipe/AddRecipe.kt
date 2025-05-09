package com.hugo.comermelhor.ui.screens.addRecipe

import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringArrayResource
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRecipeScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    recipeId: Int,
    addRecipeViewModel: AddRecipeViewModel = viewModel()
) {
    val state by addRecipeViewModel.uiState.collectAsState()
    LaunchedEffect(recipeId) {
        addRecipeViewModel.loadRecipeWithIngredients(recipeId = recipeId)
    }
    Scaffold(modifier = modifier.fillMaxSize(), topBar = {
        TopAppBar(
            title = { Text(stringResource(R.string.add_recipe_screen_title)) },
            navigationIcon = {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "")
                }
            })
    }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(it),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            LazyColumn(
                modifier = modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.85f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                item {
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
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(200.dp),
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

                        }
                    }
                }

            }
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(stringResource(R.string.calories, state.calories))

                PrimaryButton(
                    text = stringResource(R.string.add_recipe_btn),
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    addRecipeViewModel.addRecipeIfNotExists().invokeOnCompletion {
                        navController.navigateUp()
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
    val haptics = LocalHapticFeedback.current
    Card(modifier = modifier, elevation = CardDefaults.cardElevation(8.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(start = 8.dp)
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
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 4.dp),
                            value = ingredient.name,
                            label = { Text(stringResource(R.string.ingredient_description_label)) },
                            onValueChange = {
                                addRecipeViewModel.updateIngredient(
                                    ingredient.copy(
                                        name = it
                                    )
                                )
                            }
                        )
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedTextField(
                                modifier = Modifier
                                    .fillMaxWidth(0.5f)
                                    .padding(horizontal = 4.dp),
                                enabled = false,
                                colors = TextFieldDefaults.colors(disabledTextColor = Color.White),
                                value = ingredient.amount.toString(),
                                label = { Text(stringResource(R.string.ingredient_amount_description_label)) },
                                onValueChange = {}
                            )
                            Column(verticalArrangement = Arrangement.Center) {
                                Icon(
                                    modifier = Modifier.combinedClickable(
                                        onClick = {
                                            handleIngredientAmountChange(
                                                ingredient,
                                           + 0.25f,
                                                addRecipeViewModel
                                            )
                                        },
                                        onLongClick = {
                                            handleIngredientAmountChange(
                                                ingredient,
                                                 + 1f,
                                                addRecipeViewModel
                                            )
                                            haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                                        }
                                    ), imageVector = Icons.Default.Add, contentDescription = "")
                                Icon(
                                    modifier = Modifier.combinedClickable(
                                        onClick = {
                                            handleIngredientAmountChange(
                                                ingredient,
                                                 - 0.25f,
                                                addRecipeViewModel
                                            )
                                        },
                                        onLongClick = {
                                            handleIngredientAmountChange(
                                                ingredient,
                                                 - 1f,
                                                addRecipeViewModel
                                            )
                                        }
                                    ), imageVector = Icons.Default.Remove, contentDescription = "")
                            }
                            val ingredientUnitDropdownExpanded = remember { mutableStateOf(false) }
                            IngredientUnitSelection(
                                modifier = Modifier.fillMaxSize(),
                                items = stringArrayResource(R.array.ingredients_units).map {
                                    {
                                        DropdownMenuItem(text = { Text(it) }, onClick = {
                                            addRecipeViewModel.updateIngredient(
                                                ingredient.copy(
                                                    unit = it
                                                )
                                            )
                                            ingredientUnitDropdownExpanded.value = false
                                        })
                                    }
                                },
                                onDismissRequest = { ingredientUnitDropdownExpanded.value = false },
                                expanded = ingredientUnitDropdownExpanded.value,
                                dropdownMenuFace = {
                                    OutlinedTextField(
                                        modifier = Modifier
                                            .clickable {
                                                ingredientUnitDropdownExpanded.value = true
                                            }
                                            .fillMaxSize(),
                                        colors = TextFieldDefaults.colors(disabledTextColor = Color.White),
                                        enabled = false,
                                        value = ingredient.unit,
                                        label = { Text(stringResource(R.string.ingredient_unit_description_label)) },
                                        onValueChange = {})
                                })
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun IngredientUnitSelection(
    modifier: Modifier = Modifier,
    items: List<@Composable () -> Unit>,
    dropdownMenuFace: @Composable () -> Unit,
    expanded: Boolean = false,
    onDismissRequest: () -> Unit = { }
) {
    com.hugo.comermelhor.ui.widgets.DropdownMenu(
        modifier = modifier,
        items = items,
        dropdownMenuFace = dropdownMenuFace,
        onDismissRequest = onDismissRequest,
        expanded = expanded
    )
}

private fun handleIngredientAmountChange(
    ingredient: Ingredient,
    amountDelta: Float,
    addRecipeViewModel: AddRecipeViewModel
) {
    var newValue = ingredient.amount + amountDelta
    if (newValue < 0)
        newValue = 0f
    addRecipeViewModel.updateIngredient(
        ingredient.copy(
            amount = newValue
        )
    )
}