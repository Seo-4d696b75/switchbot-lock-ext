package com.seo4d696b75.android.switchbot_lock_ext.ui.screen.automationList

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import com.seo4d696b75.android.switchbot_lock_ext.domain.geo.LockGeofence
import com.seo4d696b75.android.switchbot_lock_ext.domain.user.UserRegistration
import com.seo4d696b75.android.switchbot_lock_ext.ui.R
import com.seo4d696b75.android.switchbot_lock_ext.ui.common.LoadingSection
import com.seo4d696b75.android.switchbot_lock_ext.ui.common.NoUserSection
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.automationList.page.AutomationListPage
import kotlinx.collections.immutable.ImmutableList

@Composable
fun AutomationListScreen(
    modifier: Modifier = Modifier,
    viewModel: AutomationListViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    AutomationListScreen(
        user = uiState.user,
        isLoading = uiState.isLoading,
        automations = uiState.automations,
        onEdit = { },
        onEnabledChanged = { _, _ -> },
        modifier = modifier.fillMaxSize(),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AutomationListScreen(
    user: UserRegistration,
    isLoading: Boolean,
    automations: ImmutableList<LockGeofence>,
    onEdit: (String) -> Unit,
    onEnabledChanged: (String, Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.top_bar_automation))
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
                label = "AutomationListScreen",
                modifier = Modifier.fillMaxSize(),
            ) {
                when (it) {
                    is UserRegistration.User -> AutomationListPage(
                        automations = automations,
                        onEdit = onEdit,
                        onEnabledChanged = onEnabledChanged,
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
                visible = isLoading,
                enter = fadeIn(),
                exit = fadeOut(),
            ) {
                LoadingSection()
            }
        }
    }
}
