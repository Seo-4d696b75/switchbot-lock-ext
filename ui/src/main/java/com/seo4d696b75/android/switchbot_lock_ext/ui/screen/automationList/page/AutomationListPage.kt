package com.seo4d696b75.android.switchbot_lock_ext.ui.screen.automationList.page

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.seo4d696b75.android.switchbot_lock_ext.domain.automation.LockAutomation
import com.seo4d696b75.android.switchbot_lock_ext.domain.automation.LockAutomationType
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.LockDevice
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.LockGroup
import com.seo4d696b75.android.switchbot_lock_ext.domain.geo.GeofenceTransition
import com.seo4d696b75.android.switchbot_lock_ext.domain.geo.LockGeofence
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.automationList.component.AutomationListSection
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.automationList.component.NoAutomationSection
import com.seo4d696b75.android.switchbot_lock_ext.ui.theme.AppTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun AutomationListPage(
    automations: ImmutableList<LockAutomation>,
    onEdit: (String) -> Unit,
    onEnabledChanged: (String, Boolean) -> Unit,
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
        if (automations.isEmpty()) {
            NoAutomationSection()
        } else {
            AutomationListSection(
                automations = automations,
                onEdit = onEdit,
                onEnabledChanged = onEnabledChanged,
            )
        }
    }
}

@Preview
@Composable
private fun AutomationListPagePreview_Empty() {
    AppTheme {
        Surface {
            AutomationListPage(
                automations = persistentListOf(),
                onEdit = {},
                onEnabledChanged = { _, _ -> },
            )
        }
    }
}

@Preview
@Composable
private fun AutomationListPagePreview() {
    AppTheme {
        Surface {
            AutomationListPage(
                automations = persistentListOf(
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
                    LockAutomation(
                        id = "shinjuku-automation",
                        name = "新宿駅",
                        type = LockAutomationType.Lock,
                        enabled = true,
                        device = LockDevice(
                            id = "shinjuku-device",
                            name = "新宿駅の鍵",
                            enableCloudService = true,
                            hubDeviceId = "hoge",
                            group = LockGroup.Disabled,
                        ),
                        geofence = LockGeofence(
                            id = "shinjuku-location",
                            lat = 35.68979454111181,
                            lng = 139.70028237975413,
                            radius = 200f,
                            enabled = false,
                            transition = GeofenceTransition.Exit,
                        ),
                    )
                ),
                onEdit = {},
                onEnabledChanged = { _, _ -> },
            )
        }
    }
}
