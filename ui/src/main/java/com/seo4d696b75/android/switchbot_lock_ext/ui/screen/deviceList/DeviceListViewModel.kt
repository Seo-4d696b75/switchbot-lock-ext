package com.seo4d696b75.android.switchbot_lock_ext.ui.screen.deviceList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.DeviceRepository
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.LockDevice
import com.seo4d696b75.android.switchbot_lock_ext.domain.user.UserRegistration
import com.seo4d696b75.android.switchbot_lock_ext.domain.user.UserRepository
import com.seo4d696b75.android.switchbot_lock_ext.ui.common.NavEvent
import com.seo4d696b75.android.switchbot_lock_ext.ui.common.NavEventPublisher
import com.seo4d696b75.android.switchbot_lock_ext.ui.common.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeviceListViewModel @Inject constructor(
    userRepository: UserRepository,
    private val deviceRepository: DeviceRepository,
) : ViewModel(), NavEventPublisher<DeviceListViewModel.Event> {

    sealed interface Event : NavEvent {
    }

    private val _event = MutableSharedFlow<Event>()
    override val event = _event.asSharedFlow()

    private val snackBarMessageFlow =
        MutableStateFlow<UiEvent<String>>(UiEvent.None)

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<DeviceListUiState> = userRepository
        .userFlow
        .flatMapLatest { user ->
            when (user) {
                is UserRegistration.User -> combine(
                    deviceRepository.deviceFlow,
                    snackBarMessageFlow,
                ) { devices, message ->
                    DeviceListUiState(
                        user = user,
                        devices = devices.toPersistentList(),
                        snackBarMessage = message,
                    )
                }

                else -> flow {
                    emit(
                        DeviceListUiState(
                            user = user,
                            devices = persistentListOf(),
                            snackBarMessage = UiEvent.None,
                        )
                    )
                }
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            DeviceListUiState.InitialValue,
        )

    fun remove(device: LockDevice) = viewModelScope.launch(Dispatchers.IO) {
        runCatching {
            deviceRepository.remove(device)
        }.onFailure {
            snackBarMessageFlow.update {
                UiEvent.Data("Failed to remove")
            }
        }
    }
}
