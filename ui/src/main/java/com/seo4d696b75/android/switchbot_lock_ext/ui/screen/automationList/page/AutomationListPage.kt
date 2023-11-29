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
import com.seo4d696b75.android.switchbot_lock_ext.domain.geo.GeofenceTransition
import com.seo4d696b75.android.switchbot_lock_ext.domain.geo.LockGeofence
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.automationList.component.AutomationListSection
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.automationList.component.NoAutomationSection
import com.seo4d696b75.android.switchbot_lock_ext.ui.theme.AppTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun AutomationListPage(
    automations: ImmutableList<LockGeofence>,
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
                    LockGeofence(
                        id = "tokyo-station",
                        name = "東京駅",
                        deviceId = "device-id",
                        lat = 35.68123,
                        lng = 139.76712,
                        radius = 100f,
                        enabled = true,
                        transition = GeofenceTransition.Enter,
                    ),
                    LockGeofence(
                        id = "shinjuku-station",
                        name = "新宿駅",
                        deviceId = "device-id",
                        lat = 35.68979454111181,
                        lng = 139.70028237975413,
                        radius = 200f,
                        enabled = false,
                        transition = GeofenceTransition.Exit,
                    ),
                ),
                onEdit = {},
                onEnabledChanged = { _, _ -> },
            )
        }
    }
}
