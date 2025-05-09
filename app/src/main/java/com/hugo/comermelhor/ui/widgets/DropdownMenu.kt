package com.hugo.comermelhor.ui.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun DropdownMenu(
    items: List<@Composable () -> Unit>,
    onDismissRequest: () -> Unit = {},
    dropdownMenuFace: @Composable () -> Unit
) {
    val ingredientUnitDropdownExpanded = remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                ingredientUnitDropdownExpanded.value = true
            }
    ) {
        dropdownMenuFace()
        androidx.compose.material3.DropdownMenu(
            modifier = Modifier.fillMaxWidth(),
            expanded = ingredientUnitDropdownExpanded.value,
            onDismissRequest = {
                ingredientUnitDropdownExpanded.value = false
                onDismissRequest()
            }) {
            items.forEach { item ->
                item()
            }
        }
    }
}