package com.seo4d696b75.android.switchbot_lock_ext.ui.screen.automationList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seo4d696b75.android.switchbot_lock_ext.domain.automation.AutomationRepository
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
import javax.inject.Inject

@HiltViewModel
class AutomationListViewModel @Inject constructor(
    userRepository: UserRepository,
    automationRepository: AutomationRepository,
) : ViewModel() {

    private val isLoadingFlow = MutableStateFlow(false)

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<AutomationListUiState> = userRepository
        .userFlow
        .flatMapLatest { user ->
            when (user) {
                is UserRegistration.User -> combine(
                    automationRepository.automationFlow,
                    isLoadingFlow,
                ) { automations, isLoading ->
                    AutomationListUiState(
                        user = user,
                        automations = automations.toPersistentList(),
                        isLoading = isLoading,
                    )
                }

                else -> flow {
                    emit(
                        AutomationListUiState(
                            user = user,
                            automations = persistentListOf(),
                            isLoading = false,
                        )
                    )
                }
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            AutomationListUiState.InitialValue,
        )
}
