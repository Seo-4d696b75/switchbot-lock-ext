package com.seo4d696b75.android.switchbot_lock_ext.ui.widget

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.currentState
import androidx.glance.state.GlanceStateDefinition
import com.seo4d696b75.android.switchbot_lock_ext.domain.widget.AppWidgetMediator
import com.seo4d696b75.android.switchbot_lock_ext.domain.widget.LockWidgetStatus
import com.seo4d696b75.android.switchbot_lock_ext.ui.theme.AppWidgetTheme
import java.io.File

class LockWidget(
    private val widgetMediator: AppWidgetMediator,
) : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            val deviceId = "E8893CF06602"
            val provider: LockWidgetStateProvider = currentState()
            val state = provider[deviceId]
            AppWidgetTheme {
                LockWidgetScreen(
                    state = state,
                    onLockCommand = {
                        widgetMediator.sendLockCommand(
                            appWidgetId,
                            deviceId ?: throw IllegalStateException(),
                            it,
                        )
                    },
                )
            }
        }
    }

    companion object {
        val PREF_KEY_DEVICE_ID = stringPreferencesKey("pref_key_device_id")
    }
}
