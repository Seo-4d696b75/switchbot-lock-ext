package com.seo4d696b75.android.switchbot_lock_ext.ui.screen.widgetConfiguration

import android.appwidget.AppWidgetManager
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seo4d696b75.android.switchbot_lock_ext.domain.async.asyncValueOf
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.DeviceRepository
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.LockDevice
import com.seo4d696b75.android.switchbot_lock_ext.domain.error.ErrorHandler
import com.seo4d696b75.android.switchbot_lock_ext.domain.user.UserRepository
import com.seo4d696b75.android.switchbot_lock_ext.domain.widget.AppWidgetMediator
import com.seo4d696b75.android.switchbot_lock_ext.domain.widget.AppWidgetType
import com.seo4d696b75.android.switchbot_lock_ext.ui.common.UiEvent
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update

class WidgetConfigurationViewModel @AssistedInject constructor(
    @Assisted savedStateHandle: SavedStateHandle,
    userRepository: UserRepository,
    private val deviceRepository: DeviceRepository,
    handler: ErrorHandler,
    private val widgetMediator: AppWidgetMediator,
    @Assisted private val appWidgetType: AppWidgetType,
) : ViewModel(), ErrorHandler by handler {

    @AssistedFactory
    interface Factory {
        fun create(
            savedStateHandle: SavedStateHandle,
            appWidgetType: AppWidgetType,
        ): WidgetConfigurationViewModel
    }

    private val onConfigurationCompletedFlow =
        MutableStateFlow<UiEvent<Unit>>(UiEvent.None)

    private val devices = asyncValueOf(deviceRepository.deviceFlow) {
        deviceRepository.refresh()
    }

    val uiState = combine(
        userRepository.userFlow,
        devices(),
        onConfigurationCompletedFlow,
    ) { user, devices, event ->
        WidgetConfigurationUiState(
            user = user,
            devices = devices,
            onConfigurationCompleted = event,
        )
    }.stateInCatching(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        WidgetConfigurationUiState.InitialValue,
    )

    fun refresh() {
        viewModelScope.launchCatching {
            devices.refresh()
        }
    }

    private val appWidgetId: Int by lazy {
        savedStateHandle[AppWidgetManager.EXTRA_APPWIDGET_ID]
            ?: AppWidgetManager.INVALID_APPWIDGET_ID
    }

    fun onDeviceSelected(device: LockDevice) = viewModelScope.launchCatching {
        widgetMediator.initializeAppWidget(
            appWidgetType,
            appWidgetId,
            device.id,
            device.name,
        )
        onConfigurationCompletedFlow.update { UiEvent.Data(Unit) }
    }
}
