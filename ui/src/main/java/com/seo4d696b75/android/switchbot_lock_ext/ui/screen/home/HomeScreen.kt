package com.seo4d696b75.android.switchbot_lock_ext.ui.screen.home

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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.seo4d696b75.android.switchbot_lock_ext.domain.async.AsyncValue
import com.seo4d696b75.android.switchbot_lock_ext.domain.status.LockStatus
import com.seo4d696b75.android.switchbot_lock_ext.domain.user.UserRegistration
import com.seo4d696b75.android.switchbot_lock_ext.theme.R
import com.seo4d696b75.android.switchbot_lock_ext.ui.common.LoadingSection
import com.seo4d696b75.android.switchbot_lock_ext.ui.common.NoUserSection
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.home.page.LockStatusPage

@Composable
fun HomeScreen(
    navigateToStatusDetail: (String) -> Unit,
    navigateToWidgetTypeSelection: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreen(
        user = uiState.user,
        devices = uiState.devices,
        onLockedChanged = viewModel::onLockedChanged,
        onRefresh = viewModel::refresh,
        showStatusDetail = navigateToStatusDetail,
        addWidget = navigateToWidgetTypeSelection,
        modifier = modifier.fillMaxSize(),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    user: UserRegistration,
    devices: AsyncValue<List<LockStatus>>,
    onLockedChanged: (String, Boolean) -> Unit,
    onRefresh: () -> Unit,
    showStatusDetail: (String) -> Unit,
    addWidget: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.top_bar_home))
                },
            )
        },
        floatingActionButton = {
            if (devices is AsyncValue.Data) {
                FloatingActionButton(onClick = onRefresh) {
                    Icon(
                        Icons.Default.Refresh,
                        contentDescription = stringResource(id = R.string.label_refresh_status),
                    )
                }
            }
        },
    ) { innerPadding ->
        Crossfade(
            targetState = user,
            label = "HomeScreen",
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            when (it) {
                is UserRegistration.User -> LockStatusPage(
                    devices = devices,
                    onRetry = onRefresh,
                    onLockedChanged = onLockedChanged,
                    showStatusDetail = showStatusDetail,
                    addWidget = addWidget,
                    modifier = Modifier.fillMaxSize(),
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
