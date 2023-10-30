package com.seo4d696b75.android.switchbot_lock_ext.ui.screen.device

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.LockDevice
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.device.page.DeviceListPage
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.device.page.NoUserDevicePage
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.launch

@Composable
fun DeviceScreen(
    modifier: Modifier = Modifier,
    viewModel: DeviceViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    DeviceScreen(
        isUserConfigured = uiState.isUserConfigured,
        devices = uiState.devices,
        isRefreshing = uiState.isRefreshing,
        snackBarMessage = uiState.snackBarMessage,
        clearSnackBarMessage = viewModel::clearSnackBarMessage,
        onRefreshClicked = viewModel::refresh,
        modifier = modifier.fillMaxSize(),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeviceScreen(
    isUserConfigured: Boolean,
    devices: ImmutableList<LockDevice>,
    isRefreshing: Boolean,
    onRefreshClicked: ()->Unit,
    snackBarMessage: String?,
    clearSnackBarMessage: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val snackBarHostState = remember {
        SnackbarHostState()
    }
    val scope = rememberCoroutineScope()

    LaunchedEffect(snackBarMessage) {
        if (!snackBarMessage.isNullOrBlank()) {
            scope.launch {
                snackBarHostState.showSnackbar(snackBarMessage)
            }
            clearSnackBarMessage()
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Device Management")
                },
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (isUserConfigured) {
                DeviceListPage(
                    devices = devices,
                    isRefreshing = isRefreshing,
                    onRefreshClicked = onRefreshClicked,
                )
            } else {
                NoUserDevicePage()
            }
        }
    }
}
