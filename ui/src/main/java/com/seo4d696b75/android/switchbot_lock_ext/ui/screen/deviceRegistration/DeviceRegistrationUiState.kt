package com.seo4d696b75.android.switchbot_lock_ext.ui.screen.deviceRegistration

import androidx.compose.runtime.Immutable
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.LockDevice
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class DeviceRegistrationUiState(
    val isLoading: Boolean,
    val isError: Boolean,
    val devices: ImmutableList<DeviceSelectUiState>,
    val isRegisterEnabled: Boolean,
) {
    companion object {
        val InitialValue = DeviceRegistrationUiState(
            isLoading = true,
            isError = false,
            devices = persistentListOf(),
            isRegisterEnabled = false,
        )
    }
}

@Immutable
data class DeviceSelectUiState(
    val device: LockDevice,
    val isSelected: Boolean,
)
