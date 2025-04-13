package com.seo4d696b75.android.switchbot_lock_ext.ui.screen.device

import androidx.compose.runtime.Immutable
import com.seo4d696b75.android.switchbot_lock_ext.domain.async.AsyncValue
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.LockDevice
import com.seo4d696b75.android.switchbot_lock_ext.ui.common.UiEvent

@Immutable
data class SelectDeviceUiState(
    val devices: AsyncValue<List<LockDevice>>,
    val onCompleted: UiEvent<LockDevice>,
) {
    companion object {
        val InitialValue = SelectDeviceUiState(
            devices = AsyncValue.Loading(),
            onCompleted = UiEvent.None,
        )
    }
}
