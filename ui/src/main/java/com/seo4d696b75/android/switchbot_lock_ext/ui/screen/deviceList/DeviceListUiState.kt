package com.seo4d696b75.android.switchbot_lock_ext.ui.screen.deviceList

import androidx.compose.runtime.Immutable
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.LockDevice
import com.seo4d696b75.android.switchbot_lock_ext.domain.user.UserRegistration
import com.seo4d696b75.android.switchbot_lock_ext.ui.common.UiEvent
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class DeviceListUiState(
    val user: UserRegistration,
    val devices: ImmutableList<LockDevice>,
    val isRefreshing: Boolean,
    val showRefreshErrorMessage: UiEvent<Unit>,
) {
    companion object {
        val InitialValue = DeviceListUiState(
            user = UserRegistration.Loading,
            devices = persistentListOf(),
            isRefreshing = false,
            showRefreshErrorMessage = UiEvent.None,
        )
    }
}
