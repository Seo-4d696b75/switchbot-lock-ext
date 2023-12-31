package com.seo4d696b75.android.switchbot_lock_ext.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AutoStartReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            Log.d("AutoStartReceiver", "ACTION_BOOT_COMPLETED received")

            Intent(
                context,
                LockService::class.java,
            ).apply {
                putExtra(
                    LockService.KEY_WHEN_STARTS,
                    LockService.START_BOOT_COMPLETED,
                )
            }.also(context::startService)
        }
    }
}
