package com.hugo.comermelhor.ui.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.hugo.comermelhor.R

@Composable
fun Loading(modifier: Modifier = Modifier) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        CircularProgressIndicator(modifier = modifier)
        Text(text = stringResource(R.string.loading_label))
    }
}