package com.seo4d696b75.android.switchbot_lock_ext.widget.simpleLock

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.currentState
import com.seo4d696b75.android.switchbot_lock_ext.theme.AppWidgetTheme
import kotlinx.serialization.json.Json

class SimpleLockWidget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            val pref: Preferences = currentState()
            val deviceId = pref[PREF_KEY_DEVICE_ID]
            val deviceName = pref[PREF_KEY_DEVICE_NAME]
            val status = pref[PREF_KEY_STATUS]?.let {
                Json.decodeFromString(SimpleLockWidgetStatus.serializer(), it)
            }
            AppWidgetTheme {
                SimpleLockWidgetScreen(
                    name = deviceName,
                    status = status,
                    onLockCommand = {
                        SimpleLockWorker.sendLockCommand(
                            context = context,
                            glanceId = id,
                            deviceId = requireNotNull(deviceId),
                        )
                    },
                )
            }
        }
    }

    suspend fun initialize(
        context: Context,
        glanceId: GlanceId,
        deviceId: String,
        deviceName: String,
    ) {
        updateAppWidgetState(context, glanceId) {
            it[PREF_KEY_DEVICE_ID] = deviceId
            it[PREF_KEY_DEVICE_NAME] = deviceName
            it[PREF_KEY_STATUS] = Json.encodeToString(
                SimpleLockWidgetStatus.serializer(),
                SimpleLockWidgetStatus.Idling,
            )
        }
        update(context, glanceId)
    }

    suspend fun setStatus(
        context: Context,
        glanceId: GlanceId,
        status: SimpleLockWidgetStatus,
    ) {
        updateAppWidgetState(context, glanceId) {
            it[PREF_KEY_STATUS] = Json.encodeToString(
                SimpleLockWidgetStatus.serializer(),
                status,
            )
        }
        update(context, glanceId)
    }

    companion object {
        private val PREF_KEY_DEVICE_ID =
            stringPreferencesKey("pref_key_simple_lock_widget_device_id")
        private val PREF_KEY_DEVICE_NAME =
            stringPreferencesKey("pref_key_simple_lock_widget_device_name")
        private val PREF_KEY_STATUS =
            stringPreferencesKey("pref_key_simple_lock_widget_status")
    }
}
