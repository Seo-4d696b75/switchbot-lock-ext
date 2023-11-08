package com.seo4d696b75.android.switchbot_lock_ext.ui.screen.deviceRegistration.component

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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.seo4d696b75.android.switchbot_lock_ext.ui.common.PrimaryButton
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.deviceRegistration.DeviceSelectUiState
import kotlinx.collections.immutable.ImmutableList

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DeviceRegistrationListSection(
    devices: ImmutableList<DeviceSelectUiState>,
    onDeviceSelectedChange: (String, Boolean) -> Unit,
    isRegisterEnabled: Boolean,
    onRegisterClicked: () -> Unit,
    onCancelClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            stickyHeader {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        Icons.Outlined.Info,
                        contentDescription = null,
                        modifier = Modifier.size(40.dp),
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Only controllable devices can be registered.\n- Cloud service MUST be enabled\n- Device is not grouped with others, or is a master among the grouped devices",
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
            items(
                devices,
                key = { it.device.id },
            ) { state ->
                DeviceRegistrationListItem(
                    state = state,
                    onSelectedChange = {
                        onDeviceSelectedChange(state.device.id, it)
                    },
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            PrimaryButton(
                label = "Cancel",
                onClick = onCancelClicked,
                modifier = Modifier.weight(1f),
            )
            Spacer(modifier = Modifier.width(8.dp))
            PrimaryButton(
                label = "Register",
                onClick = onRegisterClicked,
                enabled = isRegisterEnabled,
                modifier = Modifier.weight(1f),
            )
        }
    }
}
