package com.seo4d696b75.android.switchbot_lock_ext.domain.device

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LockDeviceStatus(
    val battery: Int,
    val version: String,
    val lockState: LockState,
    val doorState: String,
    val calibrate: Boolean,
)

@Serializable
enum class LockState {
    @SerialName("locked")
    Locked,
    @SerialName("unlocked")
    Unlocked,
    @SerialName("jammed")
    Jammed,
}
