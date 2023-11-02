package com.seo4d696b75.android.switchbot_lock_ext.ui.screen.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.home.page.LockListPage
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.home.page.NoUserHomePage
import kotlinx.collections.immutable.ImmutableList

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreen(
        isUserConfigured = uiState.isUserConfigured,
        devices = uiState.devices,
        onLockedChanged = viewModel::onLockedChanged,
        modifier = modifier.fillMaxSize(),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    isUserConfigured: Boolean,
    devices: ImmutableList<DeviceState>,
    onLockedChanged: suspend (String, Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Home")
                },
            )
        },
    ) { innerPadding ->
        if (isUserConfigured) {
            LockListPage(
                devices = devices,
                onLockedChanged = onLockedChanged,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
            )
        } else {
            NoUserHomePage(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
            )
        }
    }
}
