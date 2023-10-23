package com.seo4d696b75.android.switchbot_lock_ext.domain.device

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LockDevice(
    @SerialName("deviceId")
    val id: String,
    @SerialName("deviceName")
    val name: String,
    val group: Boolean,
    val groupName: String?,
    val master: Boolean,
    val enableCloudService: Boolean,
    val hubDeviceId: String,
)
