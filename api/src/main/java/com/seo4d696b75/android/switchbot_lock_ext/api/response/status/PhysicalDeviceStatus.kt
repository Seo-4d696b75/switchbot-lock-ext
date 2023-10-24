package com.seo4d696b75.android.switchbot_lock_ext.api.response.status

import kotlinx.serialization.Serializable

@Serializable
data class PhysicalDeviceStatus(
    val battery: Int,
    val version: String,
    val lockState: String,
    val doorState: String,
    val calibrate: Boolean,
)
