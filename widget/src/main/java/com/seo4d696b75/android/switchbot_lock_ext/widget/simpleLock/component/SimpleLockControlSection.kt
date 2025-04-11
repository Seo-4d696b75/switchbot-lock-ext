package com.seo4d696b75.android.switchbot_lock_ext.widget.simpleLock.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.preview.ExperimentalGlancePreviewApi
import androidx.glance.preview.Preview
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import com.seo4d696b75.android.switchbot_lock_ext.theme.AppWidgetTheme
import com.seo4d696b75.android.switchbot_lock_ext.theme.R
import com.seo4d696b75.android.switchbot_lock_ext.widget.common.glanceString
import com.seo4d696b75.android.switchbot_lock_ext.widget.simpleLock.SimpleLockWidgetStatus

@Composable
fun SimpleLockControlSection(
    name: String,
    status: SimpleLockWidgetStatus,
    onLockCommand: () -> Unit,
    modifier: GlanceModifier = GlanceModifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = name,
            style = TextStyle(
                color = GlanceTheme.colors.onSurface,
                fontSize = 12.sp,
            ),
            maxLines = 1,
            modifier = GlanceModifier.padding(top = 2.dp),
        )
        Box(
            modifier = GlanceModifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            when (status) {
                SimpleLockWidgetStatus.Idling -> {
                    SimpleLockControlButton(
                        onClicked = onLockCommand,
                    )
                }

                is SimpleLockWidgetStatus.Loading -> {
                    SimpleLoadingSection(
                        message = when (status.isLocking) {
                            true -> glanceString(id = R.string.label_locking)
                            false -> glanceString(id = R.string.label_unlocking)
                            null -> ""
                        },
                    )
                }

                is SimpleLockWidgetStatus.Success -> {
                    SimpleSuccessSection(
                        message = glanceString(
                            if (status.isLocked) R.string.message_locked_state_locked else R.string.message_locked_state_unlocked,
                        ),
                    )
                }

                is SimpleLockWidgetStatus.Failure -> {
                    SimpleErrorSection(message = status.message)
                }
            }
        }
    }
}

@OptIn(ExperimentalGlancePreviewApi::class)
@Preview
@Composable
private fun SimpleLockControlSectionPreview_Idling() {
    AppWidgetTheme {
        SimpleLockControlSection(
            name = "Device Name",
            status = SimpleLockWidgetStatus.Idling,
            onLockCommand = {},
        )
    }
}

@OptIn(ExperimentalGlancePreviewApi::class)
@Preview
@Composable
private fun SimpleLockControlSectionPreview_Loading() {
    AppWidgetTheme {
        SimpleLockControlSection(
            name = "Device Name",
            status = SimpleLockWidgetStatus.SendingCommand(true),
            onLockCommand = {},
        )
    }
}

@OptIn(ExperimentalGlancePreviewApi::class)
@Preview
@Composable
private fun SimpleLockControlSectionPreview_Success() {
    AppWidgetTheme {
        SimpleLockControlSection(
            name = "Device Name",
            status = SimpleLockWidgetStatus.Success(true),
            onLockCommand = {},
        )
    }
}

@OptIn(ExperimentalGlancePreviewApi::class)
@Preview
@Composable
private fun SimpleLockControlSectionPreview_Failure() {
    AppWidgetTheme {
        SimpleLockControlSection(
            name = "Device Name",
            status = SimpleLockWidgetStatus.Failure("Error"),
            onLockCommand = {},
        )
    }
}
