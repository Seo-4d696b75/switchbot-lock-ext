package com.seo4d696b75.android.switchbot_lock_ext.domain.widget

interface AppWidgetMediator {
    fun update(deviceId: String)
    fun onLockCommand(deviceId: String, isLocked: Boolean)
}
