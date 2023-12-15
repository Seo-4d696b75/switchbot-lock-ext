package com.seo4d696b75.android.switchbot_lock_ext.ui.screen.automationList.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.seo4d696b75.android.switchbot_lock_ext.domain.automation.LockAutomation
import kotlinx.collections.immutable.ImmutableList

@Composable
fun AutomationListSection(
    automations: ImmutableList<LockAutomation>,
    onEdit: (String) -> Unit,
    onEnabledChanged: (String, Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(
            automations,
            key = { it.id },
        ) { automation ->
            AutomationListItem(
                automation = automation,
                onClick = { onEdit(automation.id) },
                onEnabledChanged = { onEnabledChanged(automation.id, it) },
            )
        }
    }
}
