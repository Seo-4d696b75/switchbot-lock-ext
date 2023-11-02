package com.seo4d696b75.android.switchbot_lock_ext.domain.status

data class LockStatus(
    val battery: Int,
    val version: String,
    val state: LockState,
    val isDoorClosed: Boolean,
    val isCalibrated: Boolean,
)

enum class LockState {
    Locked,
    Unlocked,
    Jammed,
}
