package com.seo4d696b75.android.switchbot_lock_ext.widget.simpleLock

import android.app.Notification
import android.content.Context
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.seo4d696b75.android.switchbot_lock_ext.domain.control.LockControlRepository
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.DeviceRemoteRepository
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.LockedState
import com.seo4d696b75.android.switchbot_lock_ext.domain.notification.AppNotificationChannel
import com.seo4d696b75.android.switchbot_lock_ext.theme.R
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay

@HiltWorker
class SimpleLockWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted params: WorkerParameters,
    private val deviceRepository: DeviceRemoteRepository,
    private val controlRepository: LockControlRepository,
) : CoroutineWorker(context, params) {

    override suspend fun doWork() = runCatching {
        val appWidgetId = inputData.getInt(KEY_APP_WIDGET_ID, 0)
        val deviceId = inputData.getString(KEY_DEVICE_ID)
            ?: throw IllegalArgumentException()
        val glanceAppWidgetManager = GlanceAppWidgetManager(context)
        val glanceId = glanceAppWidgetManager.getGlanceIdBy(appWidgetId)
        val widget = SimpleLockWidget()

        widget.setStatus(
            context,
            glanceId,
            SimpleLockWidgetStatus.FetchingCurrent,
        )
        runCatching {
            val current = deviceRepository.getLockDeviceStatus(deviceId)
            val lock = when (val state = current.locked) {
                is LockedState.Normal -> !state.isLocked
                else -> throw RuntimeException("unexpected current state: $state")
            }
            widget.setStatus(
                context,
                glanceId,
                SimpleLockWidgetStatus.SendingCommand(lock),
            )
            controlRepository.setLocked(deviceId, lock)
            lock
        }.onSuccess {
            widget.setStatus(
                context,
                glanceId,
                SimpleLockWidgetStatus.Success(it)
            )
        }.onFailure {
            widget.setStatus(
                context,
                glanceId,
                SimpleLockWidgetStatus.Failure(
                    context.getString(R.string.message_locked_state_error)
                ),
            )
        }
        delay(3000L)
        widget.setStatus(
            context,
            glanceId,
            SimpleLockWidgetStatus.Idling,
        )
        Result.success()
    }.getOrElse { Result.failure() }

    override suspend fun getForegroundInfo(): ForegroundInfo {
        val notification = Notification
            .Builder(context, AppNotificationChannel.LockRunner.id)
            .setSmallIcon(R.drawable.ic_lock)
            .setContentTitle(context.getString(R.string.notification_title_widget_running))
            .setContentText(context.getString(R.string.notification_text_widget_running))
            .build()
        return ForegroundInfo(FOREGROUND_NOTIFICATION_ID, notification)
    }

    companion object {
        private const val KEY_APP_WIDGET_ID = "key_app_widget_id"
        private const val KEY_DEVICE_ID = "key_device_id"

        private const val FOREGROUND_NOTIFICATION_ID = 393901

        fun sendLockCommand(
            context: Context,
            glanceId: GlanceId,
            deviceId: String,
        ) {
            val glanceAppWidgetManager = GlanceAppWidgetManager(context)
            val appWidgetId = glanceAppWidgetManager.getAppWidgetId(glanceId)
            val request = OneTimeWorkRequestBuilder<SimpleLockWorker>()
                .setInputData(
                    workDataOf(
                        KEY_APP_WIDGET_ID to appWidgetId,
                        KEY_DEVICE_ID to deviceId,
                    )
                )
                .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
                .build()
            WorkManager.getInstance(context).enqueue(request)
        }
    }
}
