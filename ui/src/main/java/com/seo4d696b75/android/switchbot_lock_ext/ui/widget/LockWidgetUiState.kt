package com.seo4d696b75.android.switchbot_lock_ext.ui.widget

import androidx.compose.runtime.Immutable
import com.seo4d696b75.android.switchbot_lock_ext.domain.widget.LockWidgetStatus

@Immutable
sealed interface LockWidgetUiState {

    data object Initializing : LockWidgetUiState

    data class Data(
        val deviceId: String,
        val deviceName: String,
        val status: LockWidgetStatus,
    ) : LockWidgetUiState

    companion object {
        fun from(
            deviceId: String?,
            deviceName: String?,
            status: LockWidgetStatus?,
        ) = if (deviceId == null || deviceName == null || status == null) {
            Initializing
        } else {
            Data(deviceId, deviceName, status)
        }
    }
}
