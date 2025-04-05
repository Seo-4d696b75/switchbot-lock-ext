package com.seo4d696b75.android.switchbot_lock_ext.domain.device

data class LockDeviceState(
    val battery: Int,
    val version: String,
    val locked: LockedState,
    val isDoorClosed: Boolean,
    val isCalibrated: Boolean,
)
