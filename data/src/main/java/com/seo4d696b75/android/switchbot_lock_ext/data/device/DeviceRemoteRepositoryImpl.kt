package com.seo4d696b75.android.switchbot_lock_ext.data.device

import com.seo4d696b75.android.switchbot_lock_ext.api.client.SwitchBotApi
import com.seo4d696b75.android.switchbot_lock_ext.api.response.device.PhysicalDevice
import com.seo4d696b75.android.switchbot_lock_ext.api.response.status.PhysicalDeviceStatus
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.DeviceRemoteRepository
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.LockDevice
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.LockGroup
import com.seo4d696b75.android.switchbot_lock_ext.domain.status.LockStatus
import com.seo4d696b75.android.switchbot_lock_ext.domain.status.LockedState
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

class DeviceRemoteRepositoryImpl @Inject constructor(
    private val api: SwitchBotApi,
) : DeviceRemoteRepository {
    override suspend fun getLockDevices(): List<LockDevice> {
        val response = api.getDevices()
        return response
            .deviceList
            .filterIsInstance<PhysicalDevice.Lock>()
            .map { it.toModel() }
    }

    override suspend fun getLockDeviceStatus(id: String): LockStatus.Data {
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

private fun PhysicalDeviceStatus.toModel(): LockStatus.Data {
    return LockStatus.Data(
        battery = battery,
        version = version,
        state = when (lockState) {
            "locked" -> LockedState.Normal(isLocked = true)
            "unlocked" -> LockedState.Normal(isLocked = false)
            "jammed" -> LockedState.Jammed
            else -> throw IllegalArgumentException()
        },
        isDoorClosed = doorState == "closed",
        isCalibrated = calibrate,
    )
}

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
interface DeviceRemoteRepositoryModule {
    @Binds
    fun bindDeviceRemoteRepository(impl: DeviceRemoteRepositoryImpl): DeviceRemoteRepository
}
