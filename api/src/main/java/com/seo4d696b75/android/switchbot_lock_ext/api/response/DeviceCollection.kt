package com.seo4d696b75.android.switchbot_lock_ext.api.response

import com.seo4d696b75.android.switchbot_lock_ext.domain.device.PhysicalDevice
import kotlinx.serialization.Serializable

@Serializable
data class DeviceCollection(
    val deviceList: List<PhysicalDevice>
)
