package com.jamesthacker.countrysample.ui.common

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.jamesthacker.countrysample.R
import com.jamesthacker.countrysample.domain.result.DomainError

@Composable
fun ErrorDialog(
    error: DomainError?,
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit
) {
    val showError = remember { mutableStateOf(false) }

    if (error != null) {
        showError.value = true
        AlertDialog(
            modifier = modifier,
            title = {
                Text(stringResource(R.string.generic_error_title))
            },
            text = {
                Text(stringResource(R.string.generic_error_message))
            },
            onDismissRequest = {
                // dialog is not able to be dismissed. User must click OK
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onNavigateBack.invoke()
                    }
                ) {
                    Text(stringResource(R.string.cta_ok))
                }
            },
        )
    }
}

@Preview
@Composable
private fun PreviewErrorDialog() {
    ErrorDialog(error = DomainError.Unknown, onNavigateBack = {})
}
