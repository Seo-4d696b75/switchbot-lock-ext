package com.seo4d696b75.android.switchbot_lock_ext.ui.widget

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.currentState
import androidx.glance.state.GlanceStateDefinition
import com.seo4d696b75.android.switchbot_lock_ext.domain.widget.LockWidgetRepository
import com.seo4d696b75.android.switchbot_lock_ext.domain.widget.LockWidgetStateProvider
import com.seo4d696b75.android.switchbot_lock_ext.ui.theme.AppWidgetTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File

class LockWidget(
    private val widgetRepository: LockWidgetRepository,
    private val coroutineScope: CoroutineScope,
) : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            val deviceId = "E8893CF06602"
            val provider: LockWidgetStateProvider = currentState()
            val state = provider[deviceId]
            AppWidgetTheme {
                LockControlScreen(
                    name = "Door Lock",
                    state = LockWidgetUiState.fromModel(state),
                    onLockedChanged = {
                        coroutineScope.launch {
                            widgetRepository.setLocked(deviceId, it)
                            delay(2000L)
                            widgetRepository.setIdle(deviceId)
                        }
                    },
                )
            }
        }
    }

    override val stateDefinition: GlanceStateDefinition<LockWidgetStateProvider>
        get() = object : GlanceStateDefinition<LockWidgetStateProvider> {
            override suspend fun getDataStore(
                context: Context,
                fileKey: String
            ): DataStore<LockWidgetStateProvider> {
                return LockWidgetDataStore(widgetRepository)
            }

            override fun getLocation(context: Context, fileKey: String): File {
                throw NotImplementedError()
            }

        }

    companion object {
        val PREF_KEY_DEVICE_ID = stringPreferencesKey("pref_key_device_id")
    }
}
