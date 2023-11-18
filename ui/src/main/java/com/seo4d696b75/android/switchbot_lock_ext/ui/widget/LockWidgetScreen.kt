package com.seo4d696b75.android.switchbot_lock_ext.ui.widget

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import com.seo4d696b75.android.switchbot_lock_ext.ui.R
import com.seo4d696b75.android.switchbot_lock_ext.ui.widget.component.ImageProvider
import com.seo4d696b75.android.switchbot_lock_ext.ui.widget.component.LoadingSection
import com.seo4d696b75.android.switchbot_lock_ext.ui.widget.component.LockControlSection

@Composable
fun LockWidgetScreen(
    state: LockWidgetUiState,
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
        when (state) {
            LockWidgetUiState.Initializing -> {
                LoadingSection(message = "Initializing")
            }

            is LockWidgetUiState.Data -> {
                LockControlSection(
                    name = state.deviceName,
                    status = state.status,
                    onLockCommand = onLockCommand,
                )
            }
        }
    }
}
