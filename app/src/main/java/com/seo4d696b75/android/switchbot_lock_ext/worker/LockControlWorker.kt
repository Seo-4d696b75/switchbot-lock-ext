package com.seo4d696b75.android.switchbot_lock_ext.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.seo4d696b75.android.switchbot_lock_ext.domain.control.LockControlRepository
import com.seo4d696b75.android.switchbot_lock_ext.domain.widget.AppWidgetMediator
import com.seo4d696b75.android.switchbot_lock_ext.domain.widget.LockWidgetStatus
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@HiltWorker
class LockControlWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted params: WorkerParameters,
    private val controlRepository: LockControlRepository,
    private val widgetMediator: AppWidgetMediator,
) : CoroutineWorker(context, params) {

    private val appWidgetId: Int by lazy {
        inputData.getInt(KEY_APP_WIDGET_ID, 0)
    }

    private val deviceId: String by lazy {
        inputData.getString(KEY_DEVICE_ID) ?: throw IllegalArgumentException()
    }

    private val isLocked: Boolean by lazy {
        inputData.getBoolean(KEY_IS_LOCKED, true)
    }

    override suspend fun doWork() = runCatching {
        withContext(Dispatchers.IO) {
            widgetMediator.updateLockWidgetState(appWidgetId) {
                it.copy(status = LockWidgetStatus.Loading(isLocked))
            }
            runCatching {
                controlRepository.setLocked(deviceId, isLocked)
            }.onSuccess {
                widgetMediator.updateLockWidgetState(appWidgetId) {
                    it.copy(status = LockWidgetStatus.Success(isLocked))
                }
            }.onFailure {
                widgetMediator.updateLockWidgetState(appWidgetId) {
                    val message =
                        context.getString(com.seo4d696b75.android.switchbot_lock_ext.ui.R.string.message_locked_state_error)
                    it.copy(status = LockWidgetStatus.Failure(message))
                }
            }
            delay(3000L)
            widgetMediator.updateLockWidgetState(appWidgetId) {
                it.copy(status = LockWidgetStatus.Idling)
            }
            Result.success()
        }
    }.getOrElse { Result.failure() }

    companion object {
        const val KEY_APP_WIDGET_ID = "key_app_widget_id"
        const val KEY_IS_LOCKED = "key_is_locked"
        const val KEY_DEVICE_ID = "key_device_id"
    }
}
