package com.seo4d696b75.android.switchbot_lock_ext.domain.widget

interface LockWidgetRepository {
    fun getState(deviceId: String): LockWidgetState
    suspend fun setLocked(deviceId: String, isLocked: Boolean)
    fun setIdle(deviceId: String)
}
