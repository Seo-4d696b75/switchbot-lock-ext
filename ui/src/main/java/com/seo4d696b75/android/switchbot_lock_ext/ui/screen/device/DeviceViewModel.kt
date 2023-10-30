package com.seo4d696b75.android.switchbot_lock_ext.ui.screen.device

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.DeviceRepository
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.LockDevice
import com.seo4d696b75.android.switchbot_lock_ext.domain.user.UserRegistration
import com.seo4d696b75.android.switchbot_lock_ext.domain.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeviceViewModel @Inject constructor(
    userRepository: UserRepository,
    private val deviceRepository: DeviceRepository,
) : ViewModel() {

    private val isRefreshingFlow = MutableStateFlow(true)
    private val devicesFlow = MutableStateFlow<List<LockDevice>>(emptyList())
    private val snackBarMessageFlow = MutableStateFlow<String?>(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<DeviceUiState> = userRepository
        .userFlow
        .flatMapLatest { user ->
            when (user) {
                is UserRegistration.User -> combine(
                    devicesFlow,
                    isRefreshingFlow,
                    snackBarMessageFlow,
                ) { devices, isRefreshing, snackBarMessage ->
                    DeviceUiState(
                        isUserConfigured = true,
                        devices = devices.toPersistentList(),
                        isRefreshing = isRefreshing,
                        snackBarMessage = snackBarMessage,
                    )
                }

                UserRegistration.Undefined -> flow {
                    emit(
                        DeviceUiState(
                            isUserConfigured = false,
                            devices = persistentListOf(),
                            isRefreshing = false,
                            snackBarMessage = null,
                        )
                    )
                }
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            DeviceUiState.InitialValue,
        )

    fun refresh() = viewModelScope.launch {
        isRefreshingFlow.update { true }
        runCatching {
            devicesFlow.update {
                deviceRepository.getLockDevices()
            }
        }.onFailure {
            snackBarMessageFlow.update { "An error happened" }
        }
        isRefreshingFlow.update { false }
    }

    fun clearSnackBarMessage() {
        snackBarMessageFlow.update { null }
    }

}
