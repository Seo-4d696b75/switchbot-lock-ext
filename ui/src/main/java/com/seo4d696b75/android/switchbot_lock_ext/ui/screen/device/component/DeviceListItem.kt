package com.seo4d696b75.android.switchbot_lock_ext.ui.screen.device.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.LockDevice
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.LockGroup
import com.seo4d696b75.android.switchbot_lock_ext.theme.AppTheme
import com.seo4d696b75.android.switchbot_lock_ext.theme.R

@Composable
fun DeviceListItem(
    device: LockDevice,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick?.invoke() },
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
                    text = stringResource(id = R.string.message_device_not_controllable),
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
                    text = stringResource(
                        R.string.message_device_id,
                        device.id,
                    ),
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = stringResource(
                        R.string.message_device_group,
                        device.group.formatString(),
                    ),
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
        }
    }
}

@Composable
fun LockGroup.formatString() = when (this) {
    LockGroup.Disabled -> stringResource(id = R.string.message_device_group_disabled)
    is LockGroup.Enabled -> "$groupName (${stringResource(id = R.string.message_device_group_master)}: $isMaster)"
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
