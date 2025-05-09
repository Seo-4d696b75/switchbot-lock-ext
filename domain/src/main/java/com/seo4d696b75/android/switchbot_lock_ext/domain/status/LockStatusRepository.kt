package com.seo4d696b75.android.switchbot_lock_ext.domain.status

import com.seo4d696b75.android.switchbot_lock_ext.domain.device.LockedState
import kotlinx.coroutines.flow.Flow

interface LockStatusRepository {
    val statusFlow: Flow<List<LockStatus>?>
    suspend fun refresh()
    fun update(id: String, state: LockedState)
}
