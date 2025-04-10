package com.seo4d696b75.android.switchbot_lock_ext.ui.screen.widgetConfiguration

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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.seo4d696b75.android.switchbot_lock_ext.domain.async.AsyncValue
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.LockDevice
import com.seo4d696b75.android.switchbot_lock_ext.domain.user.UserRegistration
import com.seo4d696b75.android.switchbot_lock_ext.theme.R
import com.seo4d696b75.android.switchbot_lock_ext.ui.common.LoadingSection
import com.seo4d696b75.android.switchbot_lock_ext.ui.common.NoUserSection
import com.seo4d696b75.android.switchbot_lock_ext.ui.common.ObserveEvent
import com.seo4d696b75.android.switchbot_lock_ext.ui.error.ErrorHandler
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.widgetConfiguration.page.DeviceSelectionPage

@Composable
fun LockWidgetConfigurationScreen(
    onCompleted: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: WidgetConfigurationViewModel = viewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ObserveEvent(uiState.onConfigurationCompleted) {
        onCompleted()
    }

    ErrorHandler()

    LockWidgetConfigurationScreen(
        modifier = modifier,
        user = uiState.user,
        devices = uiState.devices,
        onSelected = viewModel::onDeviceSelected,
        onRefresh = viewModel::refresh,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LockWidgetConfigurationScreen(
    user: UserRegistration,
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
                    Text(text = stringResource(id = R.string.top_bar_widget_configuration))
                },
            )
        },
        floatingActionButton = {
            if (devices.data != null) {
                FloatingActionButton(onClick = onRefresh) {
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
        ) {
            Crossfade(
                targetState = user,
                label = "LockWidgetConfigurationScreen",
                modifier = Modifier.fillMaxSize(),
            ) {
                when (it) {
                    is UserRegistration.User -> DeviceSelectionPage(
                        devices = devices,
                        onSelected = onSelected,
                        onRefresh = onRefresh,
                    )

                    UserRegistration.Undefined -> Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        NoUserSection()
                    }

                    UserRegistration.Loading -> LoadingSection()
                }
            }
        }
    }
}
