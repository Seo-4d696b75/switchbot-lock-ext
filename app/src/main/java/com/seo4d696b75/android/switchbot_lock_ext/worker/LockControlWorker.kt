package com.seo4d696b75.android.switchbot_lock_ext.worker

import android.content.Context
import androidx.glance.appwidget.updateAll
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.seo4d696b75.android.switchbot_lock_ext.domain.control.LockControlRepository
import com.seo4d696b75.android.switchbot_lock_ext.domain.widget.AppWidgetMediator
import com.seo4d696b75.android.switchbot_lock_ext.domain.widget.LockWidgetRepository
import com.seo4d696b75.android.switchbot_lock_ext.domain.widget.LockWidgetState
import com.seo4d696b75.android.switchbot_lock_ext.ui.widget.LockWidget
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@HiltWorker
class LockControlWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted params: WorkerParameters,
    private val widgetRepository: LockWidgetRepository,
    private val controlRepository: LockControlRepository,
    private val widgetMediator: AppWidgetMediator,
) : CoroutineWorker(context, params) {

    private val deviceId: String by lazy {
        inputData.getString(KEY_DEVICE_ID) ?: throw IllegalArgumentException()
    }

    private val isLocked: Boolean by lazy {
        inputData.getBoolean(KEY_IS_LOCKED, true)
    }

    private val glanceAppWidget by lazy {
        LockWidget(widgetRepository, widgetMediator)
    }

    private suspend fun setState(state: LockWidgetState) {
        widgetRepository.setState(deviceId, state)
        // TODO update only the specified device
        glanceAppWidget.updateAll(context)
    }

    override suspend fun doWork() = runCatching {
        withContext(Dispatchers.IO) {
            setState(LockWidgetState.Loading(isLocking = isLocked))
            val result = runCatching {
                controlRepository.setLocked(deviceId, isLocked)
                isLocked
            }
            setState(LockWidgetState.Completed(result))
            delay(3000L)
            setState(LockWidgetState.Idling)
            Result.success()
        }
    }.getOrElse { Result.failure() }

    companion object {
        const val KEY_IS_LOCKED = "key_is_locked"
        const val KEY_DEVICE_ID = "key_device_id"
    }
}
