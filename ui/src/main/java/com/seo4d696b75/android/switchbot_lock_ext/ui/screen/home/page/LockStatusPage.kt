package com.seo4d696b75.android.switchbot_lock_ext.ui.screen.home.page

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.seo4d696b75.android.switchbot_lock_ext.domain.status.LockStatus
import com.seo4d696b75.android.switchbot_lock_ext.domain.user.UserRegistration
import com.seo4d696b75.android.switchbot_lock_ext.theme.AppTheme
import com.seo4d696b75.android.switchbot_lock_ext.ui.common.ErrorSection
import com.seo4d696b75.android.switchbot_lock_ext.ui.common.LoadingSection
import com.seo4d696b75.android.switchbot_lock_ext.ui.common.NoDeviceSection
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.home.HomeUiState
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.home.component.LockStatusPreviewParamProvider
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.home.component.StatusListSection
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList

@Composable
fun LockStatusPage(
    isError: Boolean,
    isLoading: Boolean,
    devices: ImmutableList<LockStatus>?,
    onRetry: () -> Unit,
    onLockedChanged: (String, Boolean) -> Unit,
    showStatusDetail: (String) -> Unit,
    modifier: Modifier = Modifier,
) {

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(
                horizontal = 8.dp,
                vertical = 16.dp,
            ),
        contentAlignment = Alignment.Center,
    ) {
        if (!devices.isNullOrEmpty()) {
            StatusListSection(
                devices = devices,
                onLockedChanged = onLockedChanged,
                showStatusDetail = showStatusDetail,
                modifier = Modifier.fillMaxSize(),
            )
        } else if (devices != null && devices.isEmpty()) {
            NoDeviceSection()
        } else if (isError) {
            ErrorSection(
                onRetryClicked = onRetry,
            )
        }

        if (isLoading) {
            LoadingSection()
        }
    }
}

private class LockStatusPagePreviewParamProvider :
    PreviewParameterProvider<HomeUiState> {
    override val values = sequenceOf(
        // initial loading
        HomeUiState(
            user = UserRegistration.Loading,
            isError = false,
            isLoading = true,
            devices = null,
        ),
        // initial error
        HomeUiState(
            user = UserRegistration.Loading,
            isError = true,
            isLoading = false,
            devices = null,
        ),
        // data
        HomeUiState(
            user = UserRegistration.Loading,
            isError = false,
            isLoading = false,
            devices = LockStatusPreviewParamProvider()
                .values
                .toPersistentList(),
        ),
        // refreshing
        HomeUiState(
            user = UserRegistration.Loading,
            isError = false,
            isLoading = true,
            devices = LockStatusPreviewParamProvider()
                .values
                .toPersistentList(),
        ),
    )
}

@Preview
@Composable
private fun LockStatusPagePreview(
    @PreviewParameter(LockStatusPagePreviewParamProvider::class)
    param: HomeUiState
) {
    AppTheme {
        Surface {
            LockStatusPage(
                isError = param.isError,
                isLoading = param.isLoading,
                devices = param.devices,
                onRetry = {},
                onLockedChanged = { _, _ -> },
                showStatusDetail = {},
            )
        }
    }
}
