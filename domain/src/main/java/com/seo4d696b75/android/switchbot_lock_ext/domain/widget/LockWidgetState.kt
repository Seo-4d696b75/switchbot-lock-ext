package com.seo4d696b75.android.switchbot_lock_ext.domain.widget

sealed interface LockWidgetState {
    data object Idling : LockWidgetState
    data class Loading(val isLocking: Boolean) : LockWidgetState
    data class Completed(val isLocked: Result<Boolean>) : LockWidgetState
}

interface LockWidgetStateProvider {
    operator fun get(deviceId: String) : LockWidgetState
}
