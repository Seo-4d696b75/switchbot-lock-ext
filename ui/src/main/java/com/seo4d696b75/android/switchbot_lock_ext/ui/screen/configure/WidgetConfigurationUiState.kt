package com.seo4d696b75.android.switchbot_lock_ext.ui.screen.configure

import androidx.annotation.FloatRange
import androidx.compose.runtime.Immutable
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.LockDevice
import com.seo4d696b75.android.switchbot_lock_ext.ui.common.UiEvent

@Immutable
sealed interface WidgetConfigurationUiState {
    val onCompleted: UiEvent<Unit>
    val onSelectDeviceRequested: UiEvent<Unit>

    data object Loading : WidgetConfigurationUiState {
        override val onCompleted = UiEvent.None
        override val onSelectDeviceRequested = UiEvent.None
    }

    data object NoUser : WidgetConfigurationUiState {
        override val onCompleted = UiEvent.None
        override val onSelectDeviceRequested = UiEvent.None
    }

    data class Configurable(
        val device: LockDevice?,
        val displayedName: String,
        @FloatRange(from = 0.25, to = 1.0)
        val opacity: Float,
        val isCompletable: Boolean,
        override val onCompleted: UiEvent<Unit>,
        override val onSelectDeviceRequested: UiEvent<Unit>,
    ) : WidgetConfigurationUiState
}
