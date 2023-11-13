package com.seo4d696b75.android.switchbot_lock_ext.service

import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.glance.appwidget.updateAll
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import com.seo4d696b75.android.switchbot_lock_ext.domain.widget.AppWidgetMediator
import com.seo4d696b75.android.switchbot_lock_ext.domain.widget.LockWidgetRepository
import com.seo4d696b75.android.switchbot_lock_ext.ui.widget.LockWidget
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LockService : LifecycleService() {

    @Inject
    lateinit var widgetRepository: LockWidgetRepository

    @Inject
    lateinit var widgetMediator: AppWidgetMediator

    private val glanceAppWidget by lazy {
        LockWidget(widgetRepository, widgetMediator)
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("AutoLockService", "onCreate")
    }

    private var firstStart: String? = null

    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int
    ): Int {
        super.onStartCommand(intent, flags, startId)
        intent ?: return START_STICKY

        intent.getStringExtra(KEY_WHEN_STARTS)?.let { start ->
            Log.d("AutoLockService", "onStartCommand start: $start")
            if (firstStart == null) {
                firstStart = start
            }
        }

        if (intent.hasExtra(KEY_WIDGET_DEVICE_ID) &&
            intent.hasExtra(KEY_WIDGET_COMMAND_LOCKED)
        ) {
            val deviceId = intent.getStringExtra(KEY_WIDGET_DEVICE_ID)
                ?: throw IllegalArgumentException()
            val isLocked =
                intent.getBooleanExtra(KEY_WIDGET_COMMAND_LOCKED, true)
            lifecycleScope.launch {
                widgetRepository.setLocked(deviceId, isLocked)
                // display result ui for 2 sec
                delay(2000L)
                widgetRepository.setIdle(deviceId)
            }
        }

        if (intent.getBooleanExtra(KEY_WIDGET_UPDATE_REQUEST, false) &&
            intent.hasExtra(KEY_WIDGET_DEVICE_ID)
        ) {
            val deviceId = intent.getStringExtra(KEY_WIDGET_DEVICE_ID)
                ?: throw IllegalArgumentException()
            // TODO update only the specified device
            lifecycleScope.launch {
                glanceAppWidget.updateAll(this@LockService)
            }
        }

        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        super.onBind(intent)
        return null
    }

    companion object {
        const val KEY_WHEN_STARTS = "key_when_starts"
        const val START_BOOT_COMPLETED = "start_boot_completed"
        const val START_ACTIVITY_LAUNCHED = "start_activity_launched"
        const val KEY_WIDGET_COMMAND_LOCKED = "key_widget_command_locked"
        const val KEY_WIDGET_UPDATE_REQUEST = "key_widget_update_request"
        const val KEY_WIDGET_DEVICE_ID = "key_widget_device_id"
    }
}
