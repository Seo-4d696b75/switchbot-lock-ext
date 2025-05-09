package com.seo4d696b75.android.switchbot_lock_ext.widget.lock.component

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
import com.seo4d696b75.android.switchbot_lock_ext.widget.lock.LockWidgetStatus

@Composable
fun LockControlSection(
    name: String,
    status: LockWidgetStatus,
    onLockCommand: (Boolean) -> Unit,
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
                fontSize = 14.sp,
            ),
            maxLines = 1,
            modifier = GlanceModifier.padding(top = 2.dp),
        )
        Box(
            modifier = GlanceModifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            when (status) {
                LockWidgetStatus.Idling -> {
                    LockControlButtonSection(
                        onClicked = onLockCommand,
                    )
                }

                is LockWidgetStatus.Loading -> {
                    LoadingSection(
                        message = glanceString(
                            if (status.isLocking) R.string.label_locking else R.string.label_unlocking,
                        ),
                    )
                }

                is LockWidgetStatus.Success -> {
                    CommandSuccessSection(
                        message = glanceString(
                            if (status.isLocked) R.string.message_locked_state_locked else R.string.message_locked_state_unlocked,
                        ),
                    )
                }

                is LockWidgetStatus.Failure -> {
                    ErrorSection(message = status.message)
                }
            }
        }
    }
}

@OptIn(ExperimentalGlancePreviewApi::class)
@Preview(widthDp = 120)
@Composable
private fun LockControlSectionPreview() {
    AppWidgetTheme {
        LockControlSection(
            name = "Device Name",
            status = LockWidgetStatus.Idling,
            onLockCommand = {},
        )
    }
}
