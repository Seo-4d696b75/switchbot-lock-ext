package com.seo4d696b75.android.switchbot_lock_ext.ui.screen.widgetConfiguration.page

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
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.LockDevice
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.LockGroup
import com.seo4d696b75.android.switchbot_lock_ext.domain.user.UserRegistration
import com.seo4d696b75.android.switchbot_lock_ext.theme.AppTheme
import com.seo4d696b75.android.switchbot_lock_ext.ui.common.ErrorSection
import com.seo4d696b75.android.switchbot_lock_ext.ui.common.LoadingSection
import com.seo4d696b75.android.switchbot_lock_ext.ui.common.NoDeviceSection
import com.seo4d696b75.android.switchbot_lock_ext.ui.common.UiEvent
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.widgetConfiguration.WidgetConfigurationUiState
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.widgetConfiguration.component.DeviceListSection
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun DeviceSelectionPage(
    isError: Boolean,
    isLoading: Boolean,
    devices: ImmutableList<LockDevice>?,
    onSelected: (LockDevice) -> Unit,
    onRefresh: () -> Unit,
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
            DeviceListSection(
                devices = devices,
                onClick = onSelected,
            )
        } else if (devices != null && devices.isEmpty()) {
            NoDeviceSection()
        } else if (isError) {
            ErrorSection(
                onRetryClicked = onRefresh,
            )
        }

        if (isLoading) {
            LoadingSection()
        }
    }
}

private class DeviceSelectionPagePreviewParamProvider :
    PreviewParameterProvider<WidgetConfigurationUiState> {
    override val values = sequenceOf(
        // initial loading
        WidgetConfigurationUiState(
            user = UserRegistration.Loading,
            isError = false,
            isLoading = true,
            devices = null,
            onConfigurationCompleted = UiEvent.None,
        ),
        // initial error
        WidgetConfigurationUiState(
            user = UserRegistration.Loading,
            isError = true,
            isLoading = false,
            devices = null,
            onConfigurationCompleted = UiEvent.None,
        ),
        // data
        WidgetConfigurationUiState(
            user = UserRegistration.Loading,
            isError = false,
            isLoading = false,
            devices = persistentListOf(
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
            onConfigurationCompleted = UiEvent.None,
        ),
        // refreshing
        WidgetConfigurationUiState(
            user = UserRegistration.Loading,
            isError = false,
            isLoading = true,
            devices = persistentListOf(
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
            onConfigurationCompleted = UiEvent.None,
        ),
    )
}

@Preview
@Composable
private fun DeviceSelectionPagePreview(
    @PreviewParameter(DeviceSelectionPagePreviewParamProvider::class)
    param: WidgetConfigurationUiState
) {
    AppTheme {
        Surface {
            DeviceSelectionPage(
                isError = param.isError,
                isLoading = param.isLoading,
                devices = param.devices,
                onSelected = {},
                onRefresh = {},
            )
        }
    }
}
