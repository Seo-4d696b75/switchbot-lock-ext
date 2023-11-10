package com.seo4d696b75.android.switchbot_lock_ext.ui.widget

import android.content.Context
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import com.seo4d696b75.android.switchbot_lock_ext.domain.status.AsyncLockStatus
import com.seo4d696b75.android.switchbot_lock_ext.domain.status.LockStatusRepository
import com.seo4d696b75.android.switchbot_lock_ext.ui.theme.AppWidgetTheme
import com.seo4d696b75.android.switchbot_lock_ext.ui.widget.component.LockControlSection

class LockWidget(
    private val statusRepository: LockStatusRepository,
) : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val id = "E8893CF06602"
        provideContent {
            AppWidgetTheme {
                val statusStore by statusRepository
                    .statusFlow
                    .collectAsState(initial = null)
                val status = statusStore?.get(id) ?: AsyncLockStatus.Loading
                LockControlSection(
                    name = "Lock",
                    status = status,
                    onLockedChanged = {},
                )
            }
        }
    }
}
