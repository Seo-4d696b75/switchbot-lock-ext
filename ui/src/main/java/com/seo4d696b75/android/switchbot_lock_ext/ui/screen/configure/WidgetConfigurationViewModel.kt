package com.seo4d696b75.android.switchbot_lock_ext.ui.screen.configure

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.DeviceRepository
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.LockDevice
import com.seo4d696b75.android.switchbot_lock_ext.domain.error.ErrorHandler
import com.seo4d696b75.android.switchbot_lock_ext.domain.user.UserRegistration
import com.seo4d696b75.android.switchbot_lock_ext.domain.user.UserRepository
import com.seo4d696b75.android.switchbot_lock_ext.domain.widget.AppWidgetConfiguration
import com.seo4d696b75.android.switchbot_lock_ext.domain.widget.AppWidgetMediator
import com.seo4d696b75.android.switchbot_lock_ext.ui.common.UiEvent
import com.seo4d696b75.android.switchbot_lock_ext.ui.navigation.typeMap
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class WidgetConfigurationViewModel @Inject constructor(
    userRepository: UserRepository,
    handler: ErrorHandler,
    private val widgetMediator: AppWidgetMediator,
    private val deviceRepository: DeviceRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel(), ErrorHandler by handler {

    private val args: Screen.Configuration.Top =
        savedStateHandle.toRoute(typeMap)

    private val onConfigurationCompletedFlow =
        MutableStateFlow<UiEvent<Unit>>(UiEvent.None)
    private val onSelectDeviceRequestedFlow =
        MutableStateFlow<UiEvent<Unit>>(UiEvent.None)

    private val deviceFlow = MutableStateFlow<LockDevice?>(null)
    private val nameFlow = MutableStateFlow("")
    private val opacityFlow = MutableStateFlow<Float>(1f)

    private val initializedDeviceFlow = flow {
        val conf = widgetMediator.getConfiguration(
            args.appWidgetId,
            args.appWidgetType,
        )
        if (conf != null) {
            nameFlow.update { conf.deviceName }
            opacityFlow.update { conf.opacity }
        }
        val deviceId = conf?.deviceId ?: args.initialDeviceId
        if (deviceId != null) {
            val devices = deviceRepository.deviceFlow.filterNotNull().first()
            val device = devices.firstOrNull { it.id == deviceId }
            if (device == null) {
                enqueueThrowable(RuntimeException("device not found. id:${deviceId}"))
            } else {
                deviceFlow.update { device }
                nameFlow.update { it.ifEmpty { device.name } }
            }
        }
        emitAll(deviceFlow)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState = userRepository.userFlow.flatMapLatest { user ->
        when (user) {
            is UserRegistration.User -> combine(
                onConfigurationCompletedFlow,
                onSelectDeviceRequestedFlow,
                initializedDeviceFlow,
                nameFlow,
                opacityFlow,
            ) { onCompleted, onRequested, device, name, opacity ->
                WidgetConfigurationUiState.Configurable(
                    device = device,
                    displayedName = name,
                    opacity = opacity,
                    isCompletable = device != null,
                    onCompleted = onCompleted,
                    onSelectDeviceRequested = onRequested,
                )
            }

            UserRegistration.Loading -> flowOf(WidgetConfigurationUiState.Loading)

            UserRegistration.Undefined -> flowOf(WidgetConfigurationUiState.NoUser)
        }
    }.stateInCatching(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        WidgetConfigurationUiState.Loading,
    )

    fun onDeviceSelected(device: LockDevice) {
        deviceFlow.update { device }
        nameFlow.update { device.name }
    }

    fun onDisplayedNameChanged(name: String) {
        nameFlow.update { name }
    }

    fun onBackgroundOpacityChanged(opacity: Float) {
        opacityFlow.update {
            opacity.coerceIn(0.25f, 1f)
        }
    }

    fun onCompleted() {
        val device = deviceFlow.value ?: return
        val configuration = AppWidgetConfiguration(
            type = args.appWidgetType,
            deviceId = device.id,
            deviceName = nameFlow.value,
            opacity = opacityFlow.value,
        )
        viewModelScope.launchCatching {
            widgetMediator.configure(args.appWidgetId, configuration)
            onConfigurationCompletedFlow.update { UiEvent.Data(Unit) }
        }
    }

    fun onSelectDeviceRequested() {
        onSelectDeviceRequestedFlow.update { UiEvent.Data(Unit) }
    }
}
