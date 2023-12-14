package com.seo4d696b75.android.switchbot_lock_ext.widget

import android.content.Context
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.seo4d696b75.android.switchbot_lock_ext.domain.widget.AppWidgetMediator
import com.seo4d696b75.android.switchbot_lock_ext.domain.widget.LockWidgetState
import com.seo4d696b75.android.switchbot_lock_ext.domain.widget.LockWidgetStatus
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import javax.inject.Inject

class AppWidgetMediatorImpl @Inject constructor(
    @ApplicationContext
    private val context: Context,
) : AppWidgetMediator {

    private val glanceAppWidgetManager = GlanceAppWidgetManager(context)

    override fun sendLockCommand(
        appWidgetId: Int,
        deviceId: String,
        isLocked: Boolean,
    ) {
        val request = OneTimeWorkRequestBuilder<LockControlWorker>()
            .setInputData(
                workDataOf(
                    LockControlWorker.KEY_APP_WIDGET_ID to appWidgetId,
                    LockControlWorker.KEY_DEVICE_ID to deviceId,
                    LockControlWorker.KEY_IS_LOCKED to isLocked,
                )
            )
            .build()
        WorkManager.getInstance(context).enqueue(request)
    }

    override suspend fun initializeLockWidgetState(
        appWidgetId: Int,
        deviceId: String,
        deviceName: String,
    ) {
        val glanceId = glanceAppWidgetManager.getGlanceIdBy(appWidgetId)
        updateAppWidgetState(context, glanceId) {
            it[LockWidget.PREF_KEY_STATE] = Json.encodeToString(
                LockWidgetState.serializer(),
                LockWidgetState(
                    deviceId = deviceId,
                    deviceName = deviceName,
                    status = LockWidgetStatus.Idling,
                ),
            )
        }
        LockWidget(this).update(context, glanceId)
    }

    override suspend fun updateLockWidgetState(
        appWidgetId: Int,
        update: (LockWidgetState) -> LockWidgetState
    ) {

        val glanceId = glanceAppWidgetManager.getGlanceIdBy(appWidgetId)
        updateAppWidgetState(context, glanceId) {
            val state = it[LockWidget.PREF_KEY_STATE]?.let { str ->
                Json.decodeFromString(LockWidgetState.serializer(), str)
            } ?: throw IllegalStateException()
            it[LockWidget.PREF_KEY_STATE] = Json.encodeToString(
                LockWidgetState.serializer(),
                update(state),
            )
        }
        LockWidget(this).update(context, glanceId)
    }
}

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
interface AppWidgetMediatorModule {
    @Binds
    fun bindAppWidgetMediator(impl: AppWidgetMediatorImpl): AppWidgetMediator
}
