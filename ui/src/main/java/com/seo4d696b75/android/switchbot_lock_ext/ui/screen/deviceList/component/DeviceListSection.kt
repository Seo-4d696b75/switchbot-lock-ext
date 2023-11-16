package com.seo4d696b75.android.switchbot_lock_ext.ui.screen.deviceList.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
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
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        stickyHeader {
            InfoSection(
                description = "Not all devices are controllable.\n- Cloud service MUST be enabled\n- Not grouped with others, or is master of grouped devices",
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
            )
        }
    }
}

@Composable
fun DeviceListItem(
    device: LockDevice,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
    ) {
        if (!device.isControllable) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.errorContainer)
                    .padding(horizontal = 8.dp),
            ) {
                Icon(
                    Icons.Outlined.Clear,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(24.dp),
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Not Controllable",
                    style = MaterialTheme.typography.labelSmall,
                )
            }
        }
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
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = "id: ${device.id}",
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = "group: ${device.group.formatString()}",
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
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
        )
    }
}
