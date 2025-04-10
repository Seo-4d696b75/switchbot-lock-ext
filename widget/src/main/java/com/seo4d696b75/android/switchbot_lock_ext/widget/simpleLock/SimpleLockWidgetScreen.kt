package com.seo4d696b75.android.switchbot_lock_ext.widget.simpleLock

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.ContentScale
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import com.seo4d696b75.android.switchbot_lock_ext.theme.R
import com.seo4d696b75.android.switchbot_lock_ext.widget.common.glanceString
import com.seo4d696b75.android.switchbot_lock_ext.widget.common.rememberWidgetBackground
import com.seo4d696b75.android.switchbot_lock_ext.widget.simpleLock.component.SimpleLoadingSection
import com.seo4d696b75.android.switchbot_lock_ext.widget.simpleLock.component.SimpleLockControlSection

@Composable
fun SimpleLockWidgetScreen(
    state: SimpleLockWidgetState?,
    onLockCommand: () -> Unit,
    modifier: GlanceModifier = GlanceModifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            provider = rememberWidgetBackground(
                alpha = state?.opacity ?: 1f,
            ),
            contentDescription = null,
            modifier = GlanceModifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds,
        )
        if (state == null) {
            SimpleLoadingSection(
                message = glanceString(id = R.string.message_widget_initializing),
            )
        } else {
            SimpleLockControlSection(
                name = state.deviceName,
                status = state.status,
                onLockCommand = onLockCommand,
                modifier = GlanceModifier.padding(4.dp),
            )
        }
    }
}
