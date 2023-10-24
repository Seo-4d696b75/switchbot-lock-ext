package com.seo4d696b75.android.switchbot_lock_ext.domain.device

data class LockDeviceStatus(
    val battery: Int,
    val version: String,
    val lockState: LockState,
    val isDoorClosed: Boolean,
    val isCalibrated: Boolean,
)

enum class LockState {
    Locked,
    Unlocked,
    Jammed,
}
