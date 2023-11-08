package com.seo4d696b75.android.switchbot_lock_ext.ui.screen.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seo4d696b75.android.switchbot_lock_ext.domain.control.LockControlRepository
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.DeviceRepository
import com.seo4d696b75.android.switchbot_lock_ext.domain.status.LockStatusRepository
import com.seo4d696b75.android.switchbot_lock_ext.domain.user.UserRegistration
import com.seo4d696b75.android.switchbot_lock_ext.domain.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    userRepository: UserRepository,
    private val deviceRepository: DeviceRepository,
    private val statusRepository: LockStatusRepository,
    private val controlRepository: LockControlRepository,
) : ViewModel() {

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<HomeUiState> = userRepository
        .userFlow
        .flatMapLatest { user ->
            when (user) {
                is UserRegistration.User -> combine(
                    deviceRepository.controlDeviceFlow,
                    statusRepository.statusFlow,
                ) { devices, statusStore ->
                    HomeUiState(
                        user = user,
                        devices = devices.map {
                            DeviceState(
                                device = it,
                                status = statusStore[it.id],
                            )
                        }.toPersistentList(),
                    )
                }

                else -> flow {
                    emit(
                        HomeUiState(
                            user = user,
                            devices = persistentListOf(),
                        )
                    )
                }
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            HomeUiState.InitialValue,
        )

    fun refresh() {
        statusRepository.refresh()
    }

    suspend fun onLockedChanged(id: String, locked: Boolean) {
        withContext(Dispatchers.IO) {
            Log.d("HomeViewModel", "onLockedChanged id: $id locked: $locked")
            runCatching {
                controlRepository.setLocked(id, locked)
            }.onSuccess {
                statusRepository.update(id, locked)
            }
        }
    }
}


