package com.seo4d696b75.android.switchbot_lock_ext.ui.screen.deviceRegistration

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.seo4d696b75.android.switchbot_lock_ext.ui.common.ErrorSection
import com.seo4d696b75.android.switchbot_lock_ext.ui.common.LoadingSection
import com.seo4d696b75.android.switchbot_lock_ext.ui.common.ObserveEvent
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.deviceRegistration.page.DeviceRegistrationListPage
import kotlinx.collections.immutable.ImmutableList

@Composable
fun DeviceRegistrationScreen(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    viewModel: DeviceRegistrationViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel) {
        viewModel.fetchDevices()
    }

    ObserveEvent(viewModel) {
        when (it) {
            DeviceRegistrationViewModel.Event.NavigateBack -> navigateBack()
        }
    }

    DeviceRegistrationScreen(
        isLoading = uiState.isLoading,
        isError = uiState.isError,
        devices = uiState.devices,
        onDeviceSelectedChange = viewModel::onSelectedChange,
        isRegisterEnabled = uiState.isRegisterEnabled,
        onRegisterClicked = viewModel::register,
        onRetryClicked = viewModel::fetchDevices,
        onCancelClicked = navigateBack,
        modifier = modifier.fillMaxSize(),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeviceRegistrationScreen(
    isLoading: Boolean,
    isError: Boolean,
    devices: ImmutableList<DeviceSelectUiState>,
    onDeviceSelectedChange: (String, Boolean) -> Unit,
    isRegisterEnabled: Boolean,
    onRegisterClicked: () -> Unit,
    onRetryClicked: () -> Unit,
    onCancelClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Add Device")
                },
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            Crossfade(
                targetState = isError,
                label = "DeviceRegistrationScreen:isError",
                modifier = Modifier.padding(
                    horizontal = 8.dp,
                    vertical = 16.dp,
                ),
            ) {
                if (it) {
                    ErrorSection(onRetryClicked = onRetryClicked)
                } else {
                    DeviceRegistrationListPage(
                        devices = devices,
                        onDeviceSelectedChange = onDeviceSelectedChange,
                        isRegisterEnabled = isRegisterEnabled,
                        onRegisterClicked = onRegisterClicked,
                        onCancelClicked = onCancelClicked,
                        modifier = Modifier.fillMaxSize(),
                    )
                }
            }
            LoadingSection(isLoading = isLoading)
        }
    }

}
