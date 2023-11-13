package com.seo4d696b75.android.switchbot_lock_ext.domain.widget

import kotlinx.coroutines.flow.Flow

interface LockWidgetRepository {
    val stateProviderFlow: Flow<LockWidgetStateProvider>
    suspend fun setLocked(deviceId: String, isLocked: Boolean)
    fun setIdle(deviceId: String)
}
