package com.seo4d696b75.android.switchbot_lock_ext.ui.screen.widgetConfiguration.page

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.LockDevice
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.LockGroup
import com.seo4d696b75.android.switchbot_lock_ext.theme.AppTheme
import com.seo4d696b75.android.switchbot_lock_ext.theme.R
import com.seo4d696b75.android.switchbot_lock_ext.ui.common.NoDeviceSection
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.widgetConfiguration.component.DeviceListSection
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun DeviceSelectionPage(
    devices: ImmutableList<LockDevice>,
    onSelected: (LockDevice) -> Unit,
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
        if (devices.isEmpty()) {
            NoDeviceSection(
                description = stringResource(id = R.string.description_no_selectable_device),
            )
        } else {
            DeviceListSection(
                devices = devices,
                onClick = onSelected,
            )
        }
    }
}

@Preview
@Composable
private fun DeviceSelectionPagePreview_Empty() {
    AppTheme {
        Surface {
            DeviceSelectionPage(
                devices = persistentListOf(),
                onSelected = {},
            )
        }
    }
}

@Preview
@Composable
private fun DeviceSelectionPagePreview() {
    AppTheme {
        Surface {
            DeviceSelectionPage(
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
                onSelected = {},
            )
        }
    }
}
