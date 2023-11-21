package com.seo4d696b75.android.switchbot_lock_ext.ui.screen.deviceList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.DeviceRepository
import com.seo4d696b75.android.switchbot_lock_ext.domain.user.UserRegistration
import com.seo4d696b75.android.switchbot_lock_ext.domain.user.UserRepository
import com.seo4d696b75.android.switchbot_lock_ext.ui.common.UiEvent
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
class DeviceListViewModel @Inject constructor(
    userRepository: UserRepository,
    private val deviceRepository: DeviceRepository,
) : ViewModel() {

    private val isRefreshingFlow = MutableStateFlow(false)
    private val snackBarMessageFlow =
        MutableStateFlow<UiEvent<Unit>>(UiEvent.None)

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<DeviceListUiState> = userRepository
        .userFlow
        .flatMapLatest { user ->
            when (user) {
                is UserRegistration.User -> combine(
                    deviceRepository.deviceFlow,
                    isRefreshingFlow,
                    snackBarMessageFlow,
                ) { devices, isRefreshing, message ->
                    DeviceListUiState(
                        user = user,
                        devices = devices.toPersistentList(),
                        isRefreshing = isRefreshing,
                        showRefreshErrorMessage = message,
                    )
                }

                else -> flow {
                    emit(
                        DeviceListUiState(
                            user = user,
                            devices = persistentListOf(),
                            isRefreshing = false,
                            showRefreshErrorMessage = UiEvent.None,
                        )
                    )
                }
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            DeviceListUiState.InitialValue,
        )

    fun refresh() = viewModelScope.launch {
        isRefreshingFlow.update { true }
        runCatching {
            deviceRepository.refresh()
        }.onFailure {
            snackBarMessageFlow.update { UiEvent.Data(Unit) }
        }
        isRefreshingFlow.update { false }
    }
}
