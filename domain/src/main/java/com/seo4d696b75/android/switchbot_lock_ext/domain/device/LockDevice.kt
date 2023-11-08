package com.seo4d696b75.android.switchbot_lock_ext.domain.device

data class LockDevice(
    val id: String,
    val name: String,
    val enableCloudService: Boolean,
    val hubDeviceId: String,
    val group: LockGroup,
) {
    val isMaster: Boolean
        get() = when (group) {
            LockGroup.Disabled -> true
            is LockGroup.Enabled -> group.isMaster
        }
}

sealed interface LockGroup {
    data object Disabled : LockGroup

    data class Enabled(
        val groupName: String,
        val isMaster: Boolean,
    ) : LockGroup
}
