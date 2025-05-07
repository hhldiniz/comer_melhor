package com.hugo.comermelhor.ui.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.hugo.comermelhor.R

@Composable
fun Error(
    modifier: Modifier = Modifier,
    errorViewType: ErrorViewType,
    message: String,
    retryBtnListener: (() -> Unit)? = null,
    okBtnListener: (() -> Unit)? = null,
    yesBtnOnClick: (() -> Unit)? = null,
    noBtnOnClick: (() -> Unit)? = null
) {
    Column(
        modifier.fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = message,
            fontSize = 20.sp,
            textAlign = TextAlign.Center
        )
        when (errorViewType) {
            ErrorViewType.Ok -> OkButton {

            }

            ErrorViewType.Retry -> RetryButton {

            }

            ErrorViewType.YesNo -> YesNoButtons(yesBtnOnClick = {}, noBtnOnClick = {})
        }
    }
}

@Composable
private fun YesNoButtons(
    modifier: Modifier = Modifier,
    yesBtnOnClick: () -> Unit,
    noBtnOnClick: () -> Unit
) {
    Row(modifier) {
        Button(onClick = yesBtnOnClick) {
            Text(stringResource(R.string.yes_btn_label))
        }
        Button(onClick = noBtnOnClick) {
            Text(stringResource(R.string.no_btn_label))
        }
    }
}

@Composable
private fun RetryButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.retry_message),
            textAlign = TextAlign.Center
        )
        Button(modifier = modifier, onClick = onClick) {
            Text(
                text = stringResource(R.string.retry_btn_label),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun OkButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(modifier = modifier, onClick = onClick) {

    }
}

enum class ErrorViewType {
    YesNo, Ok, Retry
}