package com.seo4d696b75.android.switchbot_lock_ext.ui.screen.deviceList

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.LockDevice
import com.seo4d696b75.android.switchbot_lock_ext.domain.user.UserRegistration
import com.seo4d696b75.android.switchbot_lock_ext.ui.common.LaunchedEvent
import com.seo4d696b75.android.switchbot_lock_ext.ui.common.LoadingSection
import com.seo4d696b75.android.switchbot_lock_ext.ui.common.NoUserSection
import com.seo4d696b75.android.switchbot_lock_ext.ui.common.UiEvent
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.deviceList.page.DeviceListPage
import kotlinx.collections.immutable.ImmutableList

@Composable
fun DeviceListScreen(
    modifier: Modifier = Modifier,
    viewModel: DeviceListViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    DeviceListScreen(
        user = uiState.user,
        devices = uiState.devices,
        snackBarMessage = uiState.snackBarMessage,
        isRefreshing = uiState.isRefreshing,
        onRefreshClicked = viewModel::refresh,
        modifier = modifier.fillMaxSize(),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeviceListScreen(
    user: UserRegistration,
    isRefreshing: Boolean,
    devices: ImmutableList<LockDevice>,
    onRefreshClicked: () -> Unit,
    snackBarMessage: UiEvent<String>,
    modifier: Modifier = Modifier,
) {
    val snackBarHostState = remember {
        SnackbarHostState()
    }

    LaunchedEvent(snackBarMessage) {
        snackBarHostState.showSnackbar(it)
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
        floatingActionButton = {
            FloatingActionButton(onClick = onRefreshClicked) {
                Icon(
                    Icons.Default.Refresh,
                    contentDescription = "refresh",
                )
            }
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
            Crossfade(
                targetState = user,
                label = "DeviceScreen",
                modifier = Modifier.fillMaxSize(),
            ) {
                when (it) {
                    is UserRegistration.User -> DeviceListPage(
                        devices = devices,
                    )

                    UserRegistration.Undefined -> Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        NoUserSection()
                    }

                    UserRegistration.Loading -> {}
                }
            }
            LoadingSection(isLoading = isRefreshing)
        }
    }
}
