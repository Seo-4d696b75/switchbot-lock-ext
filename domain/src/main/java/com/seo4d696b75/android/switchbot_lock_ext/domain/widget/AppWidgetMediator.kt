package com.seo4d696b75.android.switchbot_lock_ext.domain.widget

interface AppWidgetMediator {
    suspend fun initializeLockWidget(
        appWidgetId: Int,
        deviceId: String,
        deviceName: String
    )
}
