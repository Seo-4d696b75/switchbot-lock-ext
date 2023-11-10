package com.seo4d696b75.android.switchbot_lock_ext.ui.widget.component

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
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.layout.wrapContentHeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import com.seo4d696b75.android.switchbot_lock_ext.domain.status.AsyncLockStatus
import com.seo4d696b75.android.switchbot_lock_ext.domain.status.LockState
import com.seo4d696b75.android.switchbot_lock_ext.ui.R

@Composable
fun LockControlSection(
    name: String,
    status: AsyncLockStatus,
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
            modifier = GlanceModifier.fillMaxWidth().wrapContentHeight(),
            contentAlignment = Alignment.Center,
        ) {
            when (status) {
                is AsyncLockStatus.Data -> {
                    if (status.data.state == LockState.Jammed) {
                        ErrorSection(message = "Jammed")
                    } else {
                        LockControlButton(
                            isLocked = status.data.state == LockState.Locked,
                            onLockedChanged = onLockedChanged,
                        )
                    }
                }

                AsyncLockStatus.Error -> ErrorSection(message = "API error")
                AsyncLockStatus.Loading -> {}
            }
        }
    }
}

