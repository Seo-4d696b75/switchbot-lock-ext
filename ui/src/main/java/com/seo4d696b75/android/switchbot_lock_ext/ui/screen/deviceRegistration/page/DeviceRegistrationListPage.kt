package com.seo4d696b75.android.switchbot_lock_ext.ui.screen.deviceRegistration.page

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.LockDevice
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.LockGroup
import com.seo4d696b75.android.switchbot_lock_ext.ui.common.NoDeviceSection
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.deviceRegistration.DeviceSelectUiState
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.deviceRegistration.component.DeviceRegistrationListSection
import com.seo4d696b75.android.switchbot_lock_ext.ui.theme.AppTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun DeviceRegistrationListPage(
    devices: ImmutableList<DeviceSelectUiState>,
    onDeviceSelectedChange: (String, Boolean) -> Unit,
    isRegisterEnabled: Boolean,
    onRegisterClicked: () -> Unit,
    onCancelClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (devices.isEmpty()) {
        NoDeviceSection(
            description = "API returned no 'Lock' devices. Be sure to register your device by official SwitchBot app in advance.",
            modifier = modifier,
        )
    } else {
        DeviceRegistrationListSection(
            devices = devices,
            onDeviceSelectedChange = onDeviceSelectedChange,
            isRegisterEnabled = isRegisterEnabled,
            onRegisterClicked = onRegisterClicked,
            onCancelClicked = onCancelClicked,
            modifier = modifier,
        )
    }
}

@Preview
@Composable
private fun DeviceRegistrationListPage_Empty() {
    AppTheme {
        Surface {
            DeviceRegistrationListPage(
                devices = persistentListOf(),
                onDeviceSelectedChange = { _, _ -> },
                isRegisterEnabled = false,
                onRegisterClicked = { },
                onCancelClicked = { },
            )
        }
    }
}

@Preview
@Composable
private fun DeviceRegistrationListPage() {
    AppTheme {
        Surface {
            DeviceRegistrationListPage(
                devices = persistentListOf(
                    DeviceSelectUiState(
                        device = LockDevice(
                            id = "device-id",
                            name = "Sample Lock",
                            enableCloudService = true,
                            hubDeviceId = "hub-device-id",
                            group = LockGroup.Disabled,
                        ),
                        isSelected = true,
                    ),
                    DeviceSelectUiState(
                        device = LockDevice(
                            id = "device-id",
                            name = "Sample Lock",
                            enableCloudService = false,
                            hubDeviceId = "hub-device-id",
                            group = LockGroup.Disabled,
                        ),
                        isSelected = false,
                    ),
                    DeviceSelectUiState(
                        device = LockDevice(
                            id = "device-id",
                            name = "Sample Lock",
                            enableCloudService = true,
                            hubDeviceId = "hub-device-id",
                            group = LockGroup.Enabled(
                                groupName = "group",
                                isMaster = true,
                            ),
                        ),
                        isSelected = false,
                    ),
                    DeviceSelectUiState(
                        device = LockDevice(
                            id = "device-id",
                            name = "Sample Lock",
                            enableCloudService = true,
                            hubDeviceId = "hub-device-id",
                            group = LockGroup.Enabled(
                                groupName = "group",
                                isMaster = false,
                            ),
                        ),
                        isSelected = false,
                    ),
                ),
                onDeviceSelectedChange = { _, _ -> },
                isRegisterEnabled = true,
                onRegisterClicked = { },
                onCancelClicked = { },
            )
        }
    }
}
