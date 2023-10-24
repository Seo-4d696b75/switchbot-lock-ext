package com.seo4d696b75.android.switchbot_lock_ext.domain.device

interface DeviceRepository {
    suspend fun getLockDevices(): List<LockDevice>

    suspend fun getLockDeviceStatus(id: String): LockDeviceStatus
}