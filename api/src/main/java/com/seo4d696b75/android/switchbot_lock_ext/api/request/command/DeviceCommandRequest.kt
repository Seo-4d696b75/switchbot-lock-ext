package com.seo4d696b75.android.switchbot_lock_ext.api.request.command

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DeviceCommandRequest(
    val command: String,
    val parameter: String = "default",
    @SerialName("commandType")
    val type: String = "command",
)
