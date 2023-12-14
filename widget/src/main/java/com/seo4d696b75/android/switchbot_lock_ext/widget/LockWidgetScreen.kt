package com.seo4d696b75.android.switchbot_lock_ext.widget

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import com.seo4d696b75.android.switchbot_lock_ext.domain.widget.LockWidgetState
import com.seo4d696b75.android.switchbot_lock_ext.theme.R
import com.seo4d696b75.android.switchbot_lock_ext.widget.component.ImageProvider
import com.seo4d696b75.android.switchbot_lock_ext.widget.component.LoadingSection
import com.seo4d696b75.android.switchbot_lock_ext.widget.component.LockControlSection
import com.seo4d696b75.android.switchbot_lock_ext.widget.component.glanceString

@Composable
fun LockWidgetScreen(
    state: LockWidgetState?,
    onLockCommand: (Boolean) -> Unit,
    modifier: GlanceModifier = GlanceModifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                ImageProvider(
                    R.drawable.lock_widget_background,
                    GlanceTheme.colors.surface,
                ),
            )
            .padding(vertical = 4.dp),
        contentAlignment = Alignment.Center,
    ) {
        if (state == null) {
            LoadingSection(
                message = glanceString(
                    id = R.string.message_widget_initializing
                ),
            )
        } else {
            LockControlSection(
                name = state.deviceName,
                status = state.status,
                onLockCommand = onLockCommand,
            )
        }
    }
}
