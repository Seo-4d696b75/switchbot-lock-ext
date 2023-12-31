package com.seo4d696b75.android.switchbot_lock_ext.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.seo4d696b75.android.switchbot_lock_ext.theme.AppTheme
import com.seo4d696b75.android.switchbot_lock_ext.theme.R

@Composable
fun ErrorSection(
    modifier: Modifier = Modifier,
    title: String = stringResource(id = R.string.title_error_common),
    onRetryClicked: (() -> Unit)? = null,
) {
    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_error),
                    contentDescription = null,
                    modifier = Modifier.size(100.dp),
                    tint = MaterialTheme.colorScheme.error,
                )
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                )
            }
        }
        if (onRetryClicked != null) {
            PrimaryButton(
                label = stringResource(id = R.string.label_retry),
                onClick = onRetryClicked,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Preview
@Composable
private fun ErrorSectionPreview() {
    AppTheme {
        Surface {
            ErrorSection(
                onRetryClicked = {},
            )
        }
    }
}
