package com.seo4d696b75.android.switchbot_lock_ext.ui.screen.device

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seo4d696b75.android.switchbot_lock_ext.domain.async.asyncValueOf
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.DeviceRepository
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.LockDevice
import com.seo4d696b75.android.switchbot_lock_ext.domain.error.ErrorHandler
import com.seo4d696b75.android.switchbot_lock_ext.ui.common.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SelectDeviceViewModel @Inject constructor(
    private val deviceRepository: DeviceRepository,
    handler: ErrorHandler,
) : ViewModel(), ErrorHandler by handler {

    private val onCompletedFlow =
        MutableStateFlow<UiEvent<LockDevice>>(UiEvent.None)

    private val devices = asyncValueOf(deviceRepository.deviceFlow) {
        deviceRepository.refresh()
    }

    val uiState = combine(
        devices(),
        onCompletedFlow,
    ) { devices, event ->
        SelectDeviceUiState(
            devices = devices,
            onCompleted = event,
        )
    }.stateInCatching(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        SelectDeviceUiState.InitialValue,
    )

    fun refresh() {
        viewModelScope.launchCatching {
            devices.refresh()
        }
    }

    fun onDeviceSelected(device: LockDevice) {
        onCompletedFlow.update { UiEvent.Data(device) }
    }
}
