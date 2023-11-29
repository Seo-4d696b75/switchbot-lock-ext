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
import com.seo4d696b75.android.switchbot_lock_ext.domain.geo.LockGeofence
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.automationList.component.NoAutomationSection
import com.seo4d696b75.android.switchbot_lock_ext.ui.theme.AppTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun AutomationListPage(
    automations: ImmutableList<LockGeofence>,
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
            // TODO
        }
    }
}

@Preview
@Composable
private fun AutomationListPagePreview_Empty() {
    AppTheme {
        Surface {
            AutomationListPage(automations = persistentListOf())
        }
    }
}
