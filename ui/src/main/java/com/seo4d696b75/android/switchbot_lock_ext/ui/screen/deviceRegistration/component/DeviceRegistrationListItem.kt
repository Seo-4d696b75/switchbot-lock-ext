package com.seo4d696b75.android.switchbot_lock_ext.ui.screen.deviceRegistration.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.seo4d696b75.android.switchbot_lock_ext.ui.R
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.deviceList.component.formatString
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.deviceRegistration.DeviceSelectUiState

@Composable
fun DeviceRegistrationListItem(
    state: DeviceSelectUiState,
    onSelectedChange: (Boolean) -> Unit,
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
                    id = if (state.device.enableCloudService) R.drawable.ic_cloud_ok else R.drawable.ic_cloud_off,
                ),
                contentDescription = null,
                modifier = Modifier.size(40.dp),
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(
                modifier = Modifier.weight(1f),
            ) {
                Text(
                    text = state.device.name,
                    style = MaterialTheme.typography.titleLarge,
                )
                Text(
                    text = "id: ${state.device.id}",
                    style = MaterialTheme.typography.titleSmall,
                )
                Text(
                    text = "group: ${state.device.group.formatString()}",
                    style = MaterialTheme.typography.titleSmall,
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Checkbox(
                checked = state.isSelected,
                onCheckedChange = onSelectedChange,
                enabled = state.device.enableCloudService && state.device.isMaster,
            )
        }
    }
}
