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
import com.seo4d696b75.android.switchbot_lock_ext.domain.async.AsyncValue
import com.seo4d696b75.android.switchbot_lock_ext.domain.status.LockStatus
import com.seo4d696b75.android.switchbot_lock_ext.theme.AppTheme
import com.seo4d696b75.android.switchbot_lock_ext.ui.common.ErrorSection
import com.seo4d696b75.android.switchbot_lock_ext.ui.common.LoadingSection
import com.seo4d696b75.android.switchbot_lock_ext.ui.common.NoDeviceSection
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.home.component.LockStatusPreviewParamProvider
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.home.component.StatusListSection
import kotlinx.collections.immutable.toPersistentList

@Composable
fun LockStatusPage(
    devices: AsyncValue<List<LockStatus>>,
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
        when (devices) {
            is AsyncValue.Loading -> {}

            is AsyncValue.Error -> {
                ErrorSection(
                    onRetryClicked = onRetry,
                )
            }

            is AsyncValue.Data -> {
                if (devices.data.isEmpty()) {
                    NoDeviceSection()
                } else {
                    StatusListSection(
                        devices = devices.data.toPersistentList(),
                        onLockedChanged = onLockedChanged,
                        showStatusDetail = showStatusDetail,
                        modifier = Modifier.fillMaxSize(),
                    )
                }
            }
        }
        if (devices.isLoading) {
            LoadingSection()
        }
    }
}

private class LockStatusPagePreviewParamProvider :
    PreviewParameterProvider<AsyncValue<List<LockStatus>>> {
    override val values = sequenceOf<AsyncValue<List<LockStatus>>>(
        // initial loading
        AsyncValue.Loading(),
        // initial error
        AsyncValue.Error(
            error = Throwable("Error"),
        ),
        // data
        AsyncValue.Data(
            data = LockStatusPreviewParamProvider()
                .values
                .toPersistentList(),
        ),
        // refreshing
        AsyncValue.Data(
            data = LockStatusPreviewParamProvider()
                .values
                .toPersistentList(),
            isLoading = true,
        ),
    )
}

@Preview
@Composable
private fun LockStatusPagePreview(
    @PreviewParameter(LockStatusPagePreviewParamProvider::class)
    devices: AsyncValue<List<LockStatus>>,
) {
    AppTheme {
        Surface {
            LockStatusPage(
                devices = devices,
                onRetry = {},
                onLockedChanged = { _, _ -> },
                showStatusDetail = {},
            )
        }
    }
}
