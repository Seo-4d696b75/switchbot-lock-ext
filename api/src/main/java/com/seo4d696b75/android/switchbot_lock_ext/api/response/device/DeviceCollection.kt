package com.seo4d696b75.android.switchbot_lock_ext.api.response.device

import kotlinx.serialization.Serializable

@Serializable
data class DeviceCollection(
    val deviceList: List<PhysicalDevice>
)
