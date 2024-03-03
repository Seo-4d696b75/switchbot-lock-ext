package com.seo4d696b75.android.switchbot_lock_ext.widget.lock

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

class LockWidget : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            val pref: Preferences = currentState()
            val state = pref[PREF_KEY_STATE]?.let {
                Json.decodeFromString(LockWidgetState.serializer(), it)
            }
            AppWidgetTheme {
                LockWidgetScreen(
                    state = state,
                    onLockCommand = {
                        LockWorker.sendLockCommand(
                            context = context,
                            glanceId = id,
                            deviceId = requireNotNull(state).deviceId,
                            isLocked = it,
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
                LockWidgetState.serializer(),
                LockWidgetState(
                    deviceId = deviceId,
                    deviceName = deviceName,
                    status = LockWidgetStatus.Idling,
                ),
            )
        }
        update(context, glanceId)
    }

    suspend fun setLockState(
        context: Context,
        glanceId: GlanceId,
        update: (LockWidgetState) -> LockWidgetState,
    ) {
        updateAppWidgetState(context, glanceId) {
            val state = it[PREF_KEY_STATE]?.let { str ->
                Json.decodeFromString(LockWidgetState.serializer(), str)
            } ?: throw IllegalStateException()
            it[PREF_KEY_STATE] = Json.encodeToString(
                LockWidgetState.serializer(),
                update(state),
            )
        }
        update(context, glanceId)
    }

    companion object {
        private val PREF_KEY_STATE =
            stringPreferencesKey("pref_key_lock_widget_state")
    }
}
