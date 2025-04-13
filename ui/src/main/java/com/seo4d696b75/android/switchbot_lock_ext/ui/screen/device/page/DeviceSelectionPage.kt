package com.seo4d696b75.android.switchbot_lock_ext.ui.screen.device.page

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
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.LockDevice
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.LockGroup
import com.seo4d696b75.android.switchbot_lock_ext.theme.AppTheme
import com.seo4d696b75.android.switchbot_lock_ext.ui.common.ErrorSection
import com.seo4d696b75.android.switchbot_lock_ext.ui.common.LoadingSection
import com.seo4d696b75.android.switchbot_lock_ext.ui.common.NoDeviceSection
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.device.SelectDeviceUiState
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.device.component.DeviceListSection
import kotlinx.collections.immutable.toPersistentList

@Composable
fun DeviceSelectionPage(
    devices: AsyncValue<List<LockDevice>>,
    onSelected: (LockDevice) -> Unit,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center,
    ) {
        when (devices) {
            is AsyncValue.Loading -> {}

            is AsyncValue.Error -> {
                ErrorSection(
                    onRetryClicked = onRefresh,
                )
            }

            is AsyncValue.Data -> {
                if (devices.data.isEmpty()) {
                    NoDeviceSection()
                } else {
                    DeviceListSection(
                        devices = devices.data.toPersistentList(),
                        onClick = onSelected,
                    )
                }
            }
        }

        if (devices.isLoading) {
            LoadingSection()
        }
    }
}

private class DeviceSelectionPagePreviewParamProvider :
    PreviewParameterProvider<AsyncValue<List<LockDevice>>> {
    override val values = sequenceOf<AsyncValue<List<LockDevice>>>(
        // initial loading
        AsyncValue.Loading(),
        // initial error
        AsyncValue.Error(
            error = RuntimeException(),
        ),
        // data
        AsyncValue.Data(
            data = listOf(
                LockDevice(
                    id = "1",
                    name = "Sample Lock",
                    enableCloudService = true,
                    hubDeviceId = "hub-device-id",
                    group = LockGroup.Disabled,
                ),
                LockDevice(
                    id = "3",
                    name = "Sample Lock",
                    enableCloudService = true,
                    hubDeviceId = "hub-device-id",
                    group = LockGroup.Enabled(
                        groupName = "group",
                        isMaster = true,
                    ),
                ),
            ),
        ),
        // refreshing
        AsyncValue.Data(
            data = listOf(
                LockDevice(
                    id = "1",
                    name = "Sample Lock",
                    enableCloudService = true,
                    hubDeviceId = "hub-device-id",
                    group = LockGroup.Disabled,
                ),
                LockDevice(
                    id = "3",
                    name = "Sample Lock",
                    enableCloudService = true,
                    hubDeviceId = "hub-device-id",
                    group = LockGroup.Enabled(
                        groupName = "group",
                        isMaster = true,
                    ),
                ),
            ),
            isLoading = true,
        ),
    )
}

@Preview
@Composable
private fun DeviceSelectionPagePreview(
    @PreviewParameter(DeviceSelectionPagePreviewParamProvider::class)
    param: SelectDeviceUiState
) {
    AppTheme {
        Surface {
            DeviceSelectionPage(
                devices = param.devices,
                onSelected = {},
                onRefresh = {},
            )
        }
    }
}
