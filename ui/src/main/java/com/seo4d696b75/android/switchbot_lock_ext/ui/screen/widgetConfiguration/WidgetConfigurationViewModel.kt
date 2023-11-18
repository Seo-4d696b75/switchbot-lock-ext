package com.seo4d696b75.android.switchbot_lock_ext.ui.screen.widgetConfiguration

import android.appwidget.AppWidgetManager
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.DeviceRepository
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.LockDevice
import com.seo4d696b75.android.switchbot_lock_ext.domain.user.UserRepository
import com.seo4d696b75.android.switchbot_lock_ext.domain.widget.AppWidgetMediator
import com.seo4d696b75.android.switchbot_lock_ext.ui.common.NavEvent
import com.seo4d696b75.android.switchbot_lock_ext.ui.common.NavEventPublisher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WidgetConfigurationViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    userRepository: UserRepository,
    deviceRepository: DeviceRepository,
    private val widgetMediator: AppWidgetMediator,
) : ViewModel(), NavEventPublisher<WidgetConfigurationViewModel.Event> {

    sealed interface Event : NavEvent {
        data object Completed : Event
    }

    private val _event = MutableSharedFlow<Event>()
    override val event = _event.asSharedFlow()

    val uiState = combine(
        userRepository.userFlow,
        deviceRepository.controlDeviceFlow,
    ) { user, devices ->
        WidgetConfigurationUiState(
            user = user,
            devices = devices.toPersistentList(),
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        WidgetConfigurationUiState.InitialValue,
    )

    private val appWidgetId: Int by lazy {
        savedStateHandle[AppWidgetManager.EXTRA_APPWIDGET_ID]
            ?: AppWidgetManager.INVALID_APPWIDGET_ID
    }

    fun onDeviceSelected(device: LockDevice) = viewModelScope.launch {
        widgetMediator.initializeLockWidgetState(
            appWidgetId,
            device.id,
            device.name,
        )
        _event.emit(Event.Completed)
    }
}
