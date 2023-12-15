package com.seo4d696b75.android.switchbot_lock_ext.ui.screen.automationList.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.seo4d696b75.android.switchbot_lock_ext.domain.automation.LockAutomation
import com.seo4d696b75.android.switchbot_lock_ext.domain.automation.LockAutomationType
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.LockDevice
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.LockGroup
import com.seo4d696b75.android.switchbot_lock_ext.domain.geo.GeofenceTransition
import com.seo4d696b75.android.switchbot_lock_ext.domain.geo.LockGeofence
import com.seo4d696b75.android.switchbot_lock_ext.theme.AppTheme
import com.seo4d696b75.android.switchbot_lock_ext.theme.R
import kotlin.math.absoluteValue

@Composable
fun AutomationListItem(
    automation: LockAutomation,
    onClick: () -> Unit,
    onEnabledChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        ) {
            Icon(
                painter = painterResource(
                    if (automation.geofence.transition == GeofenceTransition.Enter) R.drawable.ic_geofence_enter else R.drawable.ic_geofence_exit,
                ),
                contentDescription = null,
                modifier = Modifier.size(40.dp),
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(
                modifier = Modifier.weight(1f),
            ) {
                Text(
                    text = automation.name,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = stringResource(
                        R.string.message_device_name,
                        automation.device.name,
                    ),
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = stringResource(
                        R.string.message_geofence_location,
                        formatLocation(
                            automation.geofence.lat,
                            automation.geofence.lng
                        ),
                    ),
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = stringResource(
                        R.string.message_geofence_radius,
                        automation.geofence.radius,
                    ),
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Switch(
                checked = automation.enabled,
                onCheckedChange = onEnabledChanged,
            )
        }
    }
}

fun formatLocation(lat: Double, lng: Double): String {
    require(lat in -90.0..90.0)
    require(lng in -180.0..180.0)
    val latPrefix = if (lat >= 0) "N" else "S"
    val lngPrefix = if (lng >= 0) "E" else "W"
    return "%s%.4f/%s%.4f".format(
        latPrefix,
        lat.absoluteValue,
        lngPrefix,
        lng.absoluteValue,
    )
}

@Preview
@Composable
private fun AutomationListItemPreview() {
    AppTheme {
        AutomationListItem(
            automation =
            LockAutomation(
                id = "tokyo-automation",
                name = "東京駅",
                type = LockAutomationType.Unlock,
                enabled = true,
                device = LockDevice(
                    id = "tokyo-device",
                    name = "東京駅の鍵",
                    enableCloudService = true,
                    hubDeviceId = "hoge",
                    group = LockGroup.Disabled,
                ),
                geofence = LockGeofence(
                    id = "tokyo-location",
                    lat = 35.68123,
                    lng = 139.76712,
                    radius = 100f,
                    enabled = true,
                    transition = GeofenceTransition.Enter,
                ),
            ),
            onClick = { },
            onEnabledChanged = { },
        )
    }
}
