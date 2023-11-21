package com.seo4d696b75.android.switchbot_lock_ext.ui.screen.deviceList

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.LockDevice
import com.seo4d696b75.android.switchbot_lock_ext.domain.user.UserRegistration
import com.seo4d696b75.android.switchbot_lock_ext.ui.R
import com.seo4d696b75.android.switchbot_lock_ext.ui.common.LoadingSection
import com.seo4d696b75.android.switchbot_lock_ext.ui.common.NoUserSection
import com.seo4d696b75.android.switchbot_lock_ext.ui.common.ObserveEvent
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
        showRefreshErrorMessage = uiState.showRefreshErrorMessage,
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
    showRefreshErrorMessage: UiEvent<Unit>,
    modifier: Modifier = Modifier,
) {
    val snackBarHostState = remember {
        SnackbarHostState()
    }

    val errorMessage = stringResource(id = R.string.message_refresh_failure)

    ObserveEvent(showRefreshErrorMessage) {
        snackBarHostState.showSnackbar(errorMessage)
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.top_bar_device))
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onRefreshClicked) {
                Icon(
                    Icons.Default.Refresh,
                    contentDescription = stringResource(id = R.string.label_refresh),
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
            AnimatedVisibility(
                visible = isRefreshing,
                enter = fadeIn(),
                exit = fadeOut(),
            ) {
                LoadingSection()
            }
        }
    }
}
