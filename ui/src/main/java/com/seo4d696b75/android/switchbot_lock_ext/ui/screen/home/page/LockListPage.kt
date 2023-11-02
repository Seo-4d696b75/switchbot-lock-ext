package com.seo4d696b75.android.switchbot_lock_ext.ui.screen.home.page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.LockDevice
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.LockGroup
import com.seo4d696b75.android.switchbot_lock_ext.ui.common.NoDeviceSection
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.home.DeviceState
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.home.component.LockListTile
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.home.component.LockListTilePreviewParamProvider
import com.seo4d696b75.android.switchbot_lock_ext.ui.theme.AppTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

@Composable
fun LockListPage(
    devices: ImmutableList<DeviceState>,
    onLockedChanged: suspend (String, Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (devices.isEmpty()) {
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center,
        ) {
            NoDeviceSection(description = "No devices registered yet, or no master devices when grouped with others.")
        }
    } else {
        LazyVerticalGrid(
            modifier = modifier,
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(devices) {
                LockListTile(
                    state = it,
                    onLockedChanged = onLockedChanged,
                )
            }
            item(
                span = { GridItemSpan(2) },
            ) {
                Spacer(modifier = Modifier.fillMaxWidth().height(40.dp))
            }
        }
    }
}

@Preview
@Composable
private fun LockListPagePreview_Empty() {
    AppTheme {
        Surface {
            LockListPage(
                devices = persistentListOf(),
                onLockedChanged = { _, _ -> },
            )
        }
    }
}

@Preview
@Composable
private fun LockListPagePreview() {
    AppTheme {
        Surface {
            LockListPage(
                devices = LockListTilePreviewParamProvider()
                    .values
                    .map {
                        DeviceState(
                            device = LockDevice(
                                id = "device-id",
                                name = "Sample Lock",
                                enableCloudService = true,
                                hubDeviceId = "hub-device-id",
                                group = LockGroup.Disabled,
                            ),
                            status = it,
                        )
                    }
                    .toPersistentList(),
                onLockedChanged = { _, _ -> },
            )
        }
    }
}
