package com.seo4d696b75.android.switchbot_lock_ext.ui.screen.deviceList.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.LockDevice
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.LockGroup
import com.seo4d696b75.android.switchbot_lock_ext.ui.R
import com.seo4d696b75.android.switchbot_lock_ext.ui.common.InfoSection
import com.seo4d696b75.android.switchbot_lock_ext.ui.theme.AppTheme
import kotlinx.collections.immutable.ImmutableList

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DeviceListSection(
    devices: ImmutableList<LockDevice>,
    onRemoveClicked: (LockDevice) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        stickyHeader {
            InfoSection(
                description = "Deleting a device only affects this local app. Remote registration in SwitchBot won't be deleted.",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
            )
        }
        items(
            devices,
            key = { it.id },
        ) {
            DeviceListItem(
                device = it,
                onRemoveClicked = { onRemoveClicked(it) },
            )
        }
    }
}

@Composable
fun DeviceListItem(
    device: LockDevice,
    onRemoveClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        ) {
            Icon(
                painter = painterResource(
                    id = if (device.enableCloudService) R.drawable.ic_cloud_ok else R.drawable.ic_cloud_off,
                ),
                contentDescription = null,
                modifier = Modifier.size(40.dp),
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(
                modifier = Modifier.weight(1f),
            ) {
                Text(
                    text = device.name,
                    style = MaterialTheme.typography.titleLarge,
                )
                Text(
                    text = "id: ${device.id}",
                    style = MaterialTheme.typography.titleSmall,
                )
                Text(
                    text = "group: ${device.group.formatString()}",
                    style = MaterialTheme.typography.titleSmall,
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            IconButton(onClick = onRemoveClicked) {
                Icon(
                    Icons.Outlined.Delete,
                    contentDescription = "remove",
                )
            }
        }
    }
}

fun LockGroup.formatString() = when (this) {
    LockGroup.Disabled -> "none"
    is LockGroup.Enabled -> "$groupName (master: $isMaster)"
}

@Preview
@Composable
private fun DeviceListItemPreview() {
    AppTheme {
        DeviceListItem(
            device = LockDevice(
                id = "device-id",
                name = "Sample Lock",
                enableCloudService = true,
                hubDeviceId = "hub-device-id",
                group = LockGroup.Disabled,
            ),
            onRemoveClicked = {},
        )
    }
}
