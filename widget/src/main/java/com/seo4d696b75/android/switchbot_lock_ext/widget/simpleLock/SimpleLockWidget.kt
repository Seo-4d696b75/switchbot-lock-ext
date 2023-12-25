package com.seo4d696b75.android.switchbot_lock_ext.widget.simpleLock

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
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
                    onLockCommand = { /*TODO*/ },
                )
            }
        }
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
