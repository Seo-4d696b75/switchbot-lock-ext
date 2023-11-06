package com.seo4d696b75.android.switchbot_lock_ext.ui.screen.deviceRegistration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.DeviceRemoteRepository
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.DeviceRepository
import com.seo4d696b75.android.switchbot_lock_ext.ui.common.NavEvent
import com.seo4d696b75.android.switchbot_lock_ext.ui.common.NavEventPublisher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeviceRegistrationViewModel @Inject constructor(
    private val deviceRepository: DeviceRepository,
    private val remoteRepository: DeviceRemoteRepository,
) : ViewModel(), NavEventPublisher<DeviceRegistrationViewModel.Event> {

    sealed interface Event : NavEvent {
        data object NavigateBack : Event
    }

    private val _event = MutableSharedFlow<Event>()
    override val event = _event.asSharedFlow()

    private val isLoadingFlow = MutableStateFlow(true)
    private val isErrorFlow = MutableStateFlow(false)
    private val deviceFlow = MutableStateFlow<List<DeviceSelectUiState>>(
        emptyList()
    )

    val uiState = combine(
        deviceFlow,
        isLoadingFlow,
        isErrorFlow,
    ) { devices, isLoading, isError ->
        DeviceRegistrationUiState(
            isLoading = isLoading,
            isError = isError,
            devices = devices.toPersistentList(),
            isRegisterEnabled = devices.any { it.isSelected },
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        DeviceRegistrationUiState.InitialValue,
    )

    fun fetchDevices() = viewModelScope.launch(Dispatchers.IO) {
        isLoadingFlow.update { true }
        isErrorFlow.update { false }
        runCatching {
            val devices = remoteRepository.getLockDevices()
            deviceFlow.update {
                devices.map {
                    DeviceSelectUiState(
                        device = it,
                        isSelected = false,
                    )
                }
            }
        }.also {
            isLoadingFlow.update { false }
        }.onFailure {
            isErrorFlow.update { true }
        }
    }

    fun onSelectedChange(id: String, isSelected: Boolean) {
        deviceFlow.update { list ->
            list.map { state ->
                if (state.device.id == id) {
                    state.copy(isSelected = isSelected)
                } else {
                    state
                }
            }
        }
    }

    fun register() = viewModelScope.launch(Dispatchers.IO) {
        runCatching {
            val devices = deviceFlow
                .value
                .filter { it.isSelected }
                .map { it.device }
            deviceRepository.add(devices)
        }.onFailure {
            isErrorFlow.update { true }
        }.onSuccess {
            _event.emit(Event.NavigateBack)
        }
    }
}
