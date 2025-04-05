package com.seo4d696b75.android.switchbot_lock_ext.domain.status

import com.seo4d696b75.android.switchbot_lock_ext.domain.device.LockDevice
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.LockDeviceState

sealed interface LockStatus {
    val device: LockDevice

    data class Data(
        override val device: LockDevice,
        val state: LockDeviceState,
    ) : LockStatus

    /**
     * No status available while fetching at the first time
     */
    data class Loading(
        override val device: LockDevice,
    ) : LockStatus

    /**
     * No status available and an error happened while fetching
     */
    data class Error(
        override val device: LockDevice,
    ) : LockStatus
}

