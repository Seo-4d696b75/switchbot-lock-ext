package com.seo4d696b75.android.switchbot_lock_ext.ui.widget

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.provideContent
import androidx.glance.currentState
import com.seo4d696b75.android.switchbot_lock_ext.domain.widget.AppWidgetMediator
import com.seo4d696b75.android.switchbot_lock_ext.domain.widget.LockWidgetStatus
import com.seo4d696b75.android.switchbot_lock_ext.ui.theme.AppWidgetTheme
import kotlinx.serialization.json.Json

class LockWidget(
    private val widgetMediator: AppWidgetMediator,
) : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val appWidgetId = GlanceAppWidgetManager(context).getAppWidgetId(id)
        provideContent {
            val pref: Preferences = currentState()
            val deviceId = pref[PREF_KEY_DEVICE_ID]
            val status = pref[PREF_KEY_STATE]?.let {
                Json.decodeFromString(LockWidgetStatus.serializer(), it)
            }
            val state = LockWidgetUiState.from(deviceId, "Door", status)
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
        val PREF_KEY_STATE = stringPreferencesKey("pref_key_lock_widget_state")
    }
}
