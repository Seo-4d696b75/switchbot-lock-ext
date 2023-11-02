package com.seo4d696b75.android.switchbot_lock_ext.domain.device

import com.seo4d696b75.android.switchbot_lock_ext.domain.status.LockStatus

interface DeviceRemoteRepository {
    suspend fun getLockDevices(): List<LockDevice>

    suspend fun getLockDeviceStatus(id: String): LockStatus
}
