package com.seo4d696b75.android.switchbot_lock_ext.ui.screen.deviceList

import androidx.compose.runtime.Immutable
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.LockDevice
import com.seo4d696b75.android.switchbot_lock_ext.ui.common.UiEvent
import com.seo4d696b75.android.switchbot_lock_ext.domain.user.UserRegistration
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class DeviceListUiState(
    val user: UserRegistration,
    val devices: ImmutableList<LockDevice>,
    val snackBarMessage: UiEvent<String>,
) {
    companion object {
        val InitialValue = DeviceListUiState(
            user = UserRegistration.Loading,
            devices = persistentListOf(),
            snackBarMessage = UiEvent.None,
        )
    }
}
