package com.seo4d696b75.android.switchbot_lock_ext.data.device

import com.seo4d696b75.android.switchbot_lock_ext.api.client.SwitchBotApi
import com.seo4d696b75.android.switchbot_lock_ext.api.response.device.PhysicalDevice
import com.seo4d696b75.android.switchbot_lock_ext.api.response.status.PhysicalDeviceStatus
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.DeviceRepository
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.LockDevice
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.LockDeviceStatus
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.LockGroup
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.LockState
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

class DeviceRepositoryImpl @Inject constructor(
    private val api: SwitchBotApi,
) : DeviceRepository {
    override suspend fun getLockDevices(): List<LockDevice> {
        val response = api.getDevices()
        return response
            .deviceList
            .filterIsInstance<PhysicalDevice.Lock>()
            .map { it.toModel() }
    }

    override suspend fun getLockDeviceStatus(id: String): LockDeviceStatus {
        val response = api.getStatus(id)
        return response.toModel()
    }
}

private fun PhysicalDevice.Lock.toModel(): LockDevice {
    return LockDevice(
        id = id,
        name = name,
        enableCloudService = enableCloudService,
        hubDeviceId = hubDeviceId,
        group = when {
            group -> LockGroup.Enabled(
                groupName = groupName ?: "",
                isMaster = master,
            )

            else -> LockGroup.Disabled
        }
    )
}

private fun PhysicalDeviceStatus.toModel(): LockDeviceStatus {
    return LockDeviceStatus(
        battery = battery,
        version = version,
        lockState = LockState
            .values()
            .first { it.name.lowercase() == lockState },
        isDoorClosed = doorState == "closed",
        isCalibrated = calibrate,
    )
}

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
interface DeviceRepositoryModule {
    @Binds
    fun bindDeviceRepository(impl: DeviceRepositoryImpl): DeviceRepository
}
