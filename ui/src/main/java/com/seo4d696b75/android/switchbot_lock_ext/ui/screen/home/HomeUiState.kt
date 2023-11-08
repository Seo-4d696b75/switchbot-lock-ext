package com.seo4d696b75.android.switchbot_lock_ext.ui.screen.home

import androidx.compose.runtime.Immutable
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.LockDevice
import com.seo4d696b75.android.switchbot_lock_ext.domain.status.AsyncLockStatus
import com.seo4d696b75.android.switchbot_lock_ext.domain.user.UserRegistration
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class HomeUiState(
    val user: UserRegistration,
    val devices: ImmutableList<DeviceState>,
) {
    companion object {
        val InitialValue = HomeUiState(
            user = UserRegistration.Loading,
            devices = persistentListOf(),
        )
    }
}

@Immutable
data class DeviceState(
    val device: LockDevice,
    val status: AsyncLockStatus,
)
