package com.seo4d696b75.android.switchbot_lock_ext.widget

import android.content.Context
import android.content.Intent
import com.seo4d696b75.android.switchbot_lock_ext.domain.widget.AppWidgetMediator
import com.seo4d696b75.android.switchbot_lock_ext.service.LockService
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
    override fun update(deviceId: String) {
        Intent(context, LockService::class.java).apply {
            putExtra(LockService.KEY_WIDGET_DEVICE_ID, deviceId)
            putExtra(LockService.KEY_WIDGET_UPDATE_REQUEST, true)
        }.let(context::startService)
    }

    override fun onLockCommand(deviceId: String, isLocked: Boolean) {
        Intent(context, LockService::class.java).apply {
            putExtra(LockService.KEY_WIDGET_DEVICE_ID, deviceId)
            putExtra(LockService.KEY_WIDGET_COMMAND_LOCKED, isLocked)
        }.let(context::startService)
    }
}

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
interface AppWidgetMediatorModule {
    @Binds
    fun bindAppWidgetMediator(impl: AppWidgetMediatorImpl): AppWidgetMediator
}
