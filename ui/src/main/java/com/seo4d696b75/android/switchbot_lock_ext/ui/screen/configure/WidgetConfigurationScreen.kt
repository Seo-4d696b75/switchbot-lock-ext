package com.seo4d696b75.android.switchbot_lock_ext.ui.screen.configure

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
import com.seo4d696b75.android.switchbot_lock_ext.theme.R
import com.seo4d696b75.android.switchbot_lock_ext.ui.common.LoadingSection
import com.seo4d696b75.android.switchbot_lock_ext.ui.common.NoUserSection
import com.seo4d696b75.android.switchbot_lock_ext.ui.common.ObserveEvent
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.configure.page.WidgetConfigurationPage

@Composable
fun WidgetConfigurationScreen(
    onCompleted: () -> Unit,
    onSelectDeviceRequested: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: WidgetConfigurationViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ObserveEvent(uiState.onCompleted) {
        onCompleted()
    }
    ObserveEvent(uiState.onSelectDeviceRequested) {
        onSelectDeviceRequested()
    }

    WidgetConfigurationScreen(
        state = uiState,
        onDisplayedNameChanged = viewModel::onDisplayedNameChanged,
        onOpacityChanged = viewModel::onBackgroundOpacityChanged,
        complete = viewModel::onCompleted,
        selectDevice = viewModel::onSelectDeviceRequested,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WidgetConfigurationScreen(
    state: WidgetConfigurationUiState,
    onDisplayedNameChanged: (String) -> Unit,
    onOpacityChanged: (Float) -> Unit,
    complete: () -> Unit,
    selectDevice: () -> Unit,
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
            @Suppress("UnusedCrossfadeTargetStateParameter")
            Crossfade(
                targetState = state.javaClass,
                label = "LockWidgetConfigurationScreen",
                modifier = Modifier.fillMaxSize(),
            ) {
                when (state) {
                    is WidgetConfigurationUiState.Configurable -> WidgetConfigurationPage(
                        device = state.device,
                        displayedName = state.displayedName,
                        onDisplayedNameChanged = onDisplayedNameChanged,
                        opacity = state.opacity,
                        onOpacityChanged = onOpacityChanged,
                        isCompletable = state.isCompletable,
                        complete = complete,
                        selectDevice = selectDevice,
                    )

                    WidgetConfigurationUiState.NoUser -> Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        NoUserSection()
                    }

                    WidgetConfigurationUiState.Loading -> LoadingSection()
                }
            }
        }
    }
}
