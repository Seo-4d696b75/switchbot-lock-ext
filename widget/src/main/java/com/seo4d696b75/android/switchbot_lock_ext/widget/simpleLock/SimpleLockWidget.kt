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
            val state = pref[PREF_KEY_STATE]?.let {
                Json.decodeFromString(SimpleLockWidgetState.serializer(), it)
            }
            AppWidgetTheme {
                SimpleLockWidgetScreen(
                    state = state,
                    onLockCommand = {
                        SimpleLockWorker.sendLockCommand(
                            context = context,
                            glanceId = id,
                            deviceId = requireNotNull(state).deviceId,
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
            it[PREF_KEY_STATE] = Json.encodeToString(
                SimpleLockWidgetState.serializer(),
                SimpleLockWidgetState(
                    deviceId = deviceId,
                    deviceName = deviceName,
                    status = SimpleLockWidgetStatus.Idling,
                ),
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
            val state = it[PREF_KEY_STATE]?.let {
                Json.decodeFromString(SimpleLockWidgetState.serializer(), it)
            } ?: throw IllegalStateException()
            it[PREF_KEY_STATE] = Json.encodeToString(
                SimpleLockWidgetState.serializer(),
                state.copy(status = status),
            )
        }
        update(context, glanceId)
    }

    companion object {
        private val PREF_KEY_STATE =
            stringPreferencesKey("pref_key_simple_lock_widget_state")
    }
}
