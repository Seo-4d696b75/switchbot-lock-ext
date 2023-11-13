package com.seo4d696b75.android.switchbot_lock_ext.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class AutoLockService : Service() {

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
        val start = intent?.getStringExtra(KEY_WHEN_STARTS)
        Log.d("AutoLockService", "onStartCommand start: $start")
        if (firstStart == null && start != null) {
            firstStart = start
        }
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    companion object {
        const val KEY_WHEN_STARTS = "key_when_starts"
        const val START_BOOT_COMPLETED = "start_boot_completed"
        const val START_ACTIVITY_LAUNCHED = "start_activity_launched"
    }
}
