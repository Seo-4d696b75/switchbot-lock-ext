package com.seo4d696b75.android.switchbot_lock_ext.ui.error

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.seo4d696b75.android.switchbot_lock_ext.theme.R

@Composable
fun ErrorHandler() {
    val viewModel: ErrorViewModel = viewModel()
    val error by viewModel.state.collectAsStateWithLifecycle()

    error?.let {
        ErrorDialog(
            description = stringResource(
                R.string.error_dialog_message,
                it.message ?: ""
            ),
            dismiss = viewModel::dismiss,
        )
    }
}

@Composable
private fun ErrorDialog(
    description: String,
    dismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = dismiss,
        confirmButton = {
            TextButton(onClick = dismiss) {
                Text(text = stringResource(id = R.string.error_dialog_button_close))
            }
        },
        title = {
            Text(text = stringResource(id = R.string.error_dialog_title))
        },
        text = {
            Text(text = description)
        },
    )
}
