package com.seo4d696b75.android.switchbot_lock_ext.ui.screen.home.dialog

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.seo4d696b75.android.switchbot_lock_ext.domain.widget.AppWidgetType
import com.seo4d696b75.android.switchbot_lock_ext.theme.AppTheme
import com.seo4d696b75.android.switchbot_lock_ext.theme.R

@Composable
fun SelectWidgetTypeDialog(
    modifier: Modifier = Modifier,
    onSelect: (AppWidgetType) -> Unit,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        modifier = modifier,
        title = {
            Text(stringResource(R.string.title_select_widget_type))
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = stringResource(R.string.message_select_widget_type),
                    style = MaterialTheme.typography.bodyLarge,
                )
                Spacer(Modifier.height(16.dp))
                WidgetTypeSection(
                    onClick = {
                        onSelect(AppWidgetType.Standard)
                        onDismiss()
                    },
                    image = R.drawable.preview_lock_widget,
                    title = R.string.label_lock_widget,
                    description = R.string.description_lock_widget,
                )
                Spacer(Modifier.height(8.dp))
                WidgetTypeSection(
                    onClick = {
                        onSelect(AppWidgetType.Simple)
                        onDismiss()
                    },
                    image = R.drawable.preview_simple_lock_widget,
                    title = R.string.label_simple_lock_widget,
                    description = R.string.description_simple_lock_widget,
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = stringResource(id = R.string.label_close),
                )
            }
        },
    )
}

@Composable
private fun WidgetTypeSection(
    onClick: () -> Unit,
    @DrawableRes image: Int,
    @StringRes title: Int,
    @StringRes description: Int,
    modifier: Modifier = Modifier,
) {
    Card(
        onClick = onClick,
        modifier = modifier,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f),
            ) {
                Text(
                    text = stringResource(title),
                    style = MaterialTheme.typography.bodyLarge,
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = stringResource(description),
                    style = MaterialTheme.typography.labelMedium,
                )
            }
            Spacer(Modifier.width(8.dp))
            Image(
                painter = painterResource(image),
                contentDescription = stringResource(title),
                modifier = Modifier.height(60.dp),
            )
        }
    }
}

@Preview
@Composable
private fun SelectWidgetTypeDialogPreview() {
    AppTheme {
        SelectWidgetTypeDialog(
            onSelect = {},
            onDismiss = {},
        )
    }
}
