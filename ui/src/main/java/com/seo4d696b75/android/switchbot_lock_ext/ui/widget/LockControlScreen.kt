package com.seo4d696b75.android.switchbot_lock_ext.ui.widget

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import com.seo4d696b75.android.switchbot_lock_ext.ui.R
import com.seo4d696b75.android.switchbot_lock_ext.ui.widget.component.CommandSuccessSection
import com.seo4d696b75.android.switchbot_lock_ext.ui.widget.component.ErrorSection
import com.seo4d696b75.android.switchbot_lock_ext.ui.widget.component.ImageProvider
import com.seo4d696b75.android.switchbot_lock_ext.ui.widget.component.LoadingSection
import com.seo4d696b75.android.switchbot_lock_ext.ui.widget.component.LockControlButtonSection

@Composable
fun LockControlScreen(
    name: String,
    state: LockWidgetUiState,
    onLockedChanged: (Boolean) -> Unit,
    modifier: GlanceModifier = GlanceModifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                ImageProvider(
                    R.drawable.lock_widget_background,
                    GlanceTheme.colors.surface,
                ),
            )
            .padding(vertical = 4.dp),
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
            when (state) {
                LockWidgetUiState.Idling -> {
                    LockControlButtonSection(
                        onClicked = onLockedChanged,
                    )
                }

                is LockWidgetUiState.Loading -> {
                    LoadingSection(
                        message = if (state.isLocking) "Locking" else "Unlocking",
                    )
                }

                is LockWidgetUiState.CommandSuccess -> {
                    CommandSuccessSection(
                        message = if (state.isLocked) "Locked" else "Unlocked",
                    )
                }

                LockWidgetUiState.CommandFailure -> {
                    ErrorSection(message = "Error")
                }
            }
        }
    }
}
