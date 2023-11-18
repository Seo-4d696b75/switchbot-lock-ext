package com.seo4d696b75.android.switchbot_lock_ext.ui.widget.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.height
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import com.seo4d696b75.android.switchbot_lock_ext.domain.widget.LockWidgetStatus

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
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = name,
            style = TextStyle(
                color = GlanceTheme.colors.onSurface,
            ),
        )
        Spacer(modifier = GlanceModifier.height(8.dp))
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
                        message = if (status.isLocking) "Locking" else "Unlocking",
                    )
                }

                is LockWidgetStatus.Success -> {
                    CommandSuccessSection(
                        message = if (status.isLocked) "Locked" else "Unlocked",
                    )
                }

                is LockWidgetStatus.Failure -> {
                    ErrorSection(message = status.message)
                }
            }
        }
    }
}
