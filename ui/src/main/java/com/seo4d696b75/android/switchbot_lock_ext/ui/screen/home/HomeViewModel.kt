package com.seo4d696b75.android.switchbot_lock_ext.ui.screen.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seo4d696b75.android.switchbot_lock_ext.domain.async.AsyncValue
import com.seo4d696b75.android.switchbot_lock_ext.domain.async.asyncValueOf
import com.seo4d696b75.android.switchbot_lock_ext.domain.control.LockControlRepository
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.LockedState
import com.seo4d696b75.android.switchbot_lock_ext.domain.error.ErrorHandler
import com.seo4d696b75.android.switchbot_lock_ext.domain.status.LockStatusRepository
import com.seo4d696b75.android.switchbot_lock_ext.domain.user.UserRegistration
import com.seo4d696b75.android.switchbot_lock_ext.domain.user.UserRepository
import com.seo4d696b75.android.switchbot_lock_ext.domain.widget.AppWidgetMediator
import com.seo4d696b75.android.switchbot_lock_ext.domain.widget.AppWidgetType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    userRepository: UserRepository,
    private val statusRepository: LockStatusRepository,
    private val controlRepository: LockControlRepository,
    private val mediator: AppWidgetMediator,
    handler: ErrorHandler,
) : ViewModel(), ErrorHandler by handler {

    private val status = asyncValueOf(statusRepository.statusFlow) {
        statusRepository.refresh()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<HomeUiState> = userRepository
        .userFlow
        .flatMapLatest { user ->
            when (user) {
                is UserRegistration.User -> status().map {
                    HomeUiState(
                        user = user,
                        devices = it,
                    )
                }

                else -> flow {
                    emit(
                        HomeUiState(
                            user = user,
                            devices = AsyncValue.Loading(),
                        )
                    )
                }
            }
        }.stateInCatching(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            HomeUiState.InitialValue,
        )

    fun refresh() {
        viewModelScope.launchCatching {
            status.refresh()
        }
    }

    fun onLockedChanged(id: String, locked: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("HomeViewModel", "onLockedChanged id: $id locked: $locked")
            runCatching {
                statusRepository.update(
                    id,
                    LockedState.Normal(isLocked = !locked, isLoading = true),
                )
                controlRepository.setLocked(id, locked)
            }.onSuccess {
                statusRepository.update(
                    id,
                    LockedState.Normal(isLocked = locked),
                )
            }.onFailure {
                statusRepository.update(
                    id,
                    LockedState.Error,
                )
            }
        }
    }

    fun addWidget(id: String, type: AppWidgetType) {
        viewModelScope.launchCatching {
            mediator.addWidget(type, id)
        }
    }
}
