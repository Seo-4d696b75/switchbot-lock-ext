package com.seo4d696b75.android.switchbot_lock_ext.widget

import android.content.Context
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.seo4d696b75.android.switchbot_lock_ext.domain.widget.AppWidgetMediator
import com.seo4d696b75.android.switchbot_lock_ext.worker.LockControlWorker
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

class AppWidgetMediatorImpl @Inject constructor(
    @ApplicationContext
    private val context: Context,
) : AppWidgetMediator {

    override fun onLockCommand(deviceId: String, isLocked: Boolean) {
        val request = OneTimeWorkRequestBuilder<LockControlWorker>()
            .setInputData(
                workDataOf(
                    LockControlWorker.KEY_DEVICE_ID to deviceId,
                    LockControlWorker.KEY_IS_LOCKED to isLocked,
                )
            )
            .build()
        WorkManager.getInstance(context).enqueue(request)
    }
}

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
interface AppWidgetMediatorModule {
    @Binds
    fun bindAppWidgetMediator(impl: AppWidgetMediatorImpl): AppWidgetMediator
}
