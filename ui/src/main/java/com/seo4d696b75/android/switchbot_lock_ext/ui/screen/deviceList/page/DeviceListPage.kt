package com.seo4d696b75.android.switchbot_lock_ext.ui.screen.deviceList.page

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.LockDevice
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.LockGroup
import com.seo4d696b75.android.switchbot_lock_ext.ui.common.NoDeviceSection
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.deviceList.component.DeviceListSection
import com.seo4d696b75.android.switchbot_lock_ext.ui.theme.AppTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun DeviceListPage(
    devices: ImmutableList<LockDevice>,
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
                description = "No devices found. At the first time, you need to refresh.",
            )
        } else {
            DeviceListSection(
                devices = devices,
            )
        }
    }
}

@Preview
@Composable
private fun DeviceListPagePreview_Empty() {
    AppTheme {
        Surface {
            DeviceListPage(
                devices = persistentListOf(),
            )
        }
    }
}

@Preview
@Composable
private fun DeviceListPagePreview() {
    AppTheme {
        Surface {
            DeviceListPage(
                devices = persistentListOf(
                    LockDevice(
                        id = "1",
                        name = "Sample Lock",
                        enableCloudService = true,
                        hubDeviceId = "hub-device-id",
                        group = LockGroup.Disabled,
                    ),
                    LockDevice(
                        id = "2",
                        name = "Sample Lock",
                        enableCloudService = false,
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
                    LockDevice(
                        id = "4",
                        name = "Sample Lock",
                        enableCloudService = true,
                        hubDeviceId = "hub-device-id",
                        group = LockGroup.Enabled(
                            groupName = "group",
                            isMaster = false,
                        ),
                    ),
                ),
            )
        }
    }
}
