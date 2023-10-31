package com.seo4d696b75.android.switchbot_lock_ext.domain.device

import kotlinx.coroutines.flow.Flow

interface DeviceRepository {
    val deviceFlow: Flow<List<LockDevice>>
    suspend fun refresh()
}
