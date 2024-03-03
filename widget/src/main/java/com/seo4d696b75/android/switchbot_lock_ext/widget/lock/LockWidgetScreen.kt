package com.seo4d696b75.android.switchbot_lock_ext.widget.lock

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.glance.ColorFilter
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import com.seo4d696b75.android.switchbot_lock_ext.theme.R
import com.seo4d696b75.android.switchbot_lock_ext.widget.common.glanceString
import com.seo4d696b75.android.switchbot_lock_ext.widget.lock.component.LoadingSection
import com.seo4d696b75.android.switchbot_lock_ext.widget.lock.component.LockControlSection

@Composable
fun LockWidgetScreen(
    state: LockWidgetState?,
    onLockCommand: (Boolean) -> Unit,
    modifier: GlanceModifier = GlanceModifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),

        contentAlignment = Alignment.Center,
    ) {
        Image(
            provider = ImageProvider(R.drawable.lock_widget_background),
            contentDescription = null,
            colorFilter = ColorFilter.tint(GlanceTheme.colors.surface),
            modifier = GlanceModifier.fillMaxSize(),
        )
        if (state == null) {
            LoadingSection(
                message = glanceString(id = R.string.message_widget_initializing),
            )
        } else {
            LockControlSection(
                name = state.deviceName,
                status = state.status,
                onLockCommand = onLockCommand,
                modifier = GlanceModifier.padding(4.dp),
            )
        }
    }
}
