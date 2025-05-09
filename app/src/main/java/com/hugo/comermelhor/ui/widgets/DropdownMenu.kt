package com.hugo.comermelhor.ui.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DropdownMenu(
    modifier: Modifier = Modifier,
    items: List<@Composable () -> Unit>,
    expanded: Boolean = false,
    onDismissRequest: () -> Unit = {},
    dropdownMenuFace: @Composable () -> Unit
) {
    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        dropdownMenuFace()
        androidx.compose.material3.DropdownMenu(
            modifier = Modifier.fillMaxWidth(),
            expanded = expanded,
            onDismissRequest = onDismissRequest
        ) {
            items.forEach { item ->
                item()
            }
        }
    }
}