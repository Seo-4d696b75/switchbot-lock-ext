package com.seo4d696b75.android.switchbot_lock_ext.ui.screen.device

import androidx.compose.runtime.Immutable
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.LockDevice
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class DeviceUiState(
    val isUserConfigured: Boolean,
    val devices: ImmutableList<LockDevice>,
    val isRefreshing: Boolean,
    val snackBarMessage: String?,
) {
    companion object {
        val InitialValue = DeviceUiState(
            isUserConfigured = true,
            devices = persistentListOf(),
            isRefreshing = false,
            snackBarMessage = null,
        )
    }
}
