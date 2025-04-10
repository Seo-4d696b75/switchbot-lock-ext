package com.seo4d696b75.android.switchbot_lock_ext.ui.screen.widgetConfiguration

import androidx.compose.runtime.Immutable
import com.seo4d696b75.android.switchbot_lock_ext.domain.async.AsyncValue
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.LockDevice
import com.seo4d696b75.android.switchbot_lock_ext.domain.user.UserRegistration
import com.seo4d696b75.android.switchbot_lock_ext.ui.common.UiEvent

@Immutable
data class WidgetConfigurationUiState(
    val user: UserRegistration,
    val devices: AsyncValue<List<LockDevice>>,
    val onConfigurationCompleted: UiEvent<Unit>,
) {
    companion object {
        val InitialValue = WidgetConfigurationUiState(
            user = UserRegistration.Loading,
            devices = AsyncValue.Loading(),
            onConfigurationCompleted = UiEvent.None,
        )
    }
}
