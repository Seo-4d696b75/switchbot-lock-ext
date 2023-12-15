package com.seo4d696b75.android.switchbot_lock_ext.domain.automation

import com.seo4d696b75.android.switchbot_lock_ext.domain.device.LockDevice
import com.seo4d696b75.android.switchbot_lock_ext.domain.geo.LockGeofence

data class LockAutomation(
    val id: String,
    val enabled: Boolean,
    val name: String,
    val device: LockDevice,
    val type: LockAutomationType,
    val geofence: LockGeofence,
)

enum class LockAutomationType {
    Lock,
    Unlock
}
