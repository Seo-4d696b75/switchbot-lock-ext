package com.seo4d696b75.android.switchbot_lock_ext.ui.screen.widgetConfiguration

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.LockDevice
import com.seo4d696b75.android.switchbot_lock_ext.domain.user.UserRegistration
import com.seo4d696b75.android.switchbot_lock_ext.theme.R
import com.seo4d696b75.android.switchbot_lock_ext.ui.common.LoadingSection
import com.seo4d696b75.android.switchbot_lock_ext.ui.common.NoUserSection
import com.seo4d696b75.android.switchbot_lock_ext.ui.common.ObserveEvent
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.widgetConfiguration.page.DeviceSelectionPage
import kotlinx.collections.immutable.ImmutableList

@Composable
fun LockWidgetConfigurationScreen(
    onCompleted: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: WidgetConfigurationViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ObserveEvent(uiState.onConfigurationCompleted) {
        onCompleted()
    }

    LockWidgetConfigurationScreen(
        modifier = modifier,
        user = uiState.user,
        devices = uiState.devices,
        onSelected = viewModel::onDeviceSelected,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LockWidgetConfigurationScreen(
    user: UserRegistration,
    devices: ImmutableList<LockDevice>,
    onSelected: (LockDevice) -> Unit,
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
