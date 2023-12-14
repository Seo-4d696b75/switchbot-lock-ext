package com.seo4d696b75.android.switchbot_lock_ext.widget

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.provideContent
import androidx.glance.currentState
import com.seo4d696b75.android.switchbot_lock_ext.domain.widget.AppWidgetMediator
import com.seo4d696b75.android.switchbot_lock_ext.domain.widget.LockWidgetState
import com.seo4d696b75.android.switchbot_lock_ext.theme.AppWidgetTheme
import kotlinx.serialization.json.Json

class LockWidget(
    private val widgetMediator: AppWidgetMediator,
) : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val appWidgetId = GlanceAppWidgetManager(context).getAppWidgetId(id)
        provideContent {
            val pref: Preferences = currentState()
            val state = pref[PREF_KEY_STATE]?.let {
                Json.decodeFromString(LockWidgetState.serializer(), it)
            }
            AppWidgetTheme {
                LockWidgetScreen(
                    state = state,
                    onLockCommand = {
                        widgetMediator.sendLockCommand(
                            appWidgetId,
                            state?.deviceId ?: throw IllegalStateException(),
                            it,
                        )
                    },
                )
            }
        }
    }

    companion object {
        val PREF_KEY_STATE = stringPreferencesKey("pref_key_lock_widget_state")
    }
}
