package com.hugo.comermelhor.ui.widgets

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PrimaryButton(modifier: Modifier = Modifier, text: String, onClick: () -> Unit) {
    Button(modifier = modifier, onClick = onClick, shape = RoundedCornerShape(10.dp)) {
        Text(text = text)
    }
}