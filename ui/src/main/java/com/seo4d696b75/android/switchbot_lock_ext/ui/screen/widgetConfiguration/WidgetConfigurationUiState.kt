package com.seo4d696b75.android.switchbot_lock_ext.ui.screen.widgetConfiguration

import androidx.compose.runtime.Immutable
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.LockDevice
import com.seo4d696b75.android.switchbot_lock_ext.domain.user.UserRegistration
import com.seo4d696b75.android.switchbot_lock_ext.ui.common.UiEvent
import kotlinx.collections.immutable.ImmutableList

@Immutable
data class WidgetConfigurationUiState(
    val user: UserRegistration,
    val isError: Boolean,
    val isLoading: Boolean,
    val devices: ImmutableList<LockDevice>?,
    val onConfigurationCompleted: UiEvent<Unit>,
) {
    companion object {
        val InitialValue = WidgetConfigurationUiState(
            user = UserRegistration.Loading,
            isError = false,
            isLoading = true,
            devices = null,
            onConfigurationCompleted = UiEvent.None,
        )
    }
}
