package com.seo4d696b75.android.switchbot_lock_ext.ui.screen.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seo4d696b75.android.switchbot_lock_ext.domain.control.LockControlRepository
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.DeviceRepository
import com.seo4d696b75.android.switchbot_lock_ext.domain.status.AsyncLockStatus
import com.seo4d696b75.android.switchbot_lock_ext.domain.status.LockState
import com.seo4d696b75.android.switchbot_lock_ext.domain.status.LockStatusRepository
import com.seo4d696b75.android.switchbot_lock_ext.domain.status.LockStatusStore
import com.seo4d696b75.android.switchbot_lock_ext.domain.user.UserRegistration
import com.seo4d696b75.android.switchbot_lock_ext.domain.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    userRepository: UserRepository,
    private val deviceRepository: DeviceRepository,
    private val statusRepository: LockStatusRepository,
    private val controlRepository: LockControlRepository,
) : ViewModel() {

    private val lockStateCacheFlow =
        MutableStateFlow(mutableMapOf<String, Boolean>())

    private fun resolveLockStatus(
        id: String,
        store: LockStatusStore,
        lockStateCache: Map<String, Boolean>,
    ): AsyncLockStatus {
        return when (val s = store[id]) {
            is AsyncLockStatus.Data -> lockStateCache[id]?.let { locked ->
                AsyncLockStatus.Data(
                    data = s.data.copy(
                        state = if (locked) LockState.Locked else LockState.Unlocked,
                    ),
                )
            } ?: s

            else -> s
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<HomeUiState> = userRepository
        .userFlow
        .flatMapLatest { user ->
            when (user) {
                is UserRegistration.User -> combine(
                    deviceRepository.controlDeviceFlow,
                    statusRepository.statusFlow,
                    lockStateCacheFlow,
                ) { devices, statusStore, lockStateCache ->
                    HomeUiState(
                        isUserConfigured = true,
                        devices = devices.map {
                            DeviceState(
                                device = it,
                                status = resolveLockStatus(
                                    it.id,
                                    statusStore,
                                    lockStateCache
                                ),
                            )
                        }.toPersistentList(),
                    )
                }

                UserRegistration.Undefined -> flow {
                    emit(
                        HomeUiState(
                            isUserConfigured = false,
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
        lockStateCacheFlow.update {
            it.apply { clear() }
        }
    }

    suspend fun onLockedChanged(id: String, locked: Boolean) {
        withContext(Dispatchers.IO) {
            Log.d("HomeViewModel", "onLockedChanged id: $id locked: $locked")
            runCatching {
                controlRepository.setLocked(id, locked)
            }.onSuccess {
                lockStateCacheFlow.update {
                    it.apply { set(id, locked) }
                }
            }
        }
    }
}


