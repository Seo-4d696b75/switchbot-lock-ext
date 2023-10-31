package com.seo4d696b75.android.switchbot_lock_ext.ui.screen.device.page

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.LockDevice
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.LockGroup
import com.seo4d696b75.android.switchbot_lock_ext.ui.R
import com.seo4d696b75.android.switchbot_lock_ext.ui.common.LoadingSection
import com.seo4d696b75.android.switchbot_lock_ext.ui.common.PrimaryButton
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.device.component.DeviceListSection
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.device.component.NoDeviceSection
import com.seo4d696b75.android.switchbot_lock_ext.ui.theme.AppTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun DeviceListPage(
    devices: ImmutableList<LockDevice>,
    isRefreshing: Boolean,
    onRefreshClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = 8.dp,
                    vertical = 16.dp,
                ),
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center,
            ) {
                if (devices.isEmpty()) {
                    NoDeviceSection()
                } else {
                    DeviceListSection(devices = devices)
                }
            }
            PrimaryButton(
                label = "Refresh",
                enabled = !isRefreshing,
                onClick = onRefreshClicked,
                iconResId = R.drawable.ic_refresh,
                modifier = Modifier.fillMaxWidth(),
            )
        }
        LoadingSection(isLoading = isRefreshing)
    }
}

@Preview
@Composable
private fun DeviceListPagePreview_Empty() {
    AppTheme {
        Surface {
            DeviceListPage(
                devices = persistentListOf(),
                isRefreshing = false,
                onRefreshClicked = {},
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
                        id = "device-id",
                        name = "Sample Lock",
                        enableCloudService = true,
                        hubDeviceId = "hub-device-id",
                        group = LockGroup.Disabled,
                    )
                ),
                isRefreshing = false,
                onRefreshClicked = {},
            )
        }
    }
}
