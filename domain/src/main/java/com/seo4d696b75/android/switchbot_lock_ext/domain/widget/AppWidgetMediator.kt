package com.seo4d696b75.android.switchbot_lock_ext.domain.widget

interface AppWidgetMediator {
    fun sendLockCommand(appWidgetId: Int, deviceId: String, isLocked: Boolean)
    suspend fun initializeLockWidgetState(appWidgetId: Int, deviceId: String)
    suspend fun setLockWidgetState(appWidgetId: Int, state: LockWidgetStatus)
}
