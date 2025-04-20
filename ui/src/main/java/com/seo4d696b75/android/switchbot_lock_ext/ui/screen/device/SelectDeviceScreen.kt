package com.seo4d696b75.android.switchbot_lock_ext.ui.screen.device

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.seo4d696b75.android.switchbot_lock_ext.domain.async.AsyncValue
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.LockDevice
import com.seo4d696b75.android.switchbot_lock_ext.theme.R
import com.seo4d696b75.android.switchbot_lock_ext.ui.common.ObserveEvent
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.device.page.DeviceSelectionPage

@Composable
fun SelectDeviceScreen(
    onCompleted: (LockDevice) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SelectDeviceViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ObserveEvent(uiState.onCompleted) {
        onCompleted(it)
    }

    SelectDeviceScreen(
        modifier = modifier,
        devices = uiState.devices,
        onSelected = viewModel::onDeviceSelected,
        onRefresh = viewModel::refresh,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectDeviceScreen(
    devices: AsyncValue<List<LockDevice>>,
    onSelected: (LockDevice) -> Unit,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.top_bar_select_device))
                },
            )
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        floatingActionButton = {
            if (devices.data != null) {
                FloatingActionButton(
                    onClick = onRefresh,
                    modifier = Modifier.safeDrawingPadding(),
                ) {
                    Icon(
                        Icons.Default.Refresh,
                        contentDescription = stringResource(id = R.string.label_refresh_status),
                    )
                }
            }
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .consumeWindowInsets(WindowInsets.statusBars),
        ) {
            DeviceSelectionPage(
                devices = devices,
                onSelected = onSelected,
                onRefresh = onRefresh,
            )
        }
    }
}
