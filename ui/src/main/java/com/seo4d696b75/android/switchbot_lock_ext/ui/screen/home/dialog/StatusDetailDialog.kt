package com.seo4d696b75.android.switchbot_lock_ext.ui.screen.home.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.LockDevice
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.LockGroup
import com.seo4d696b75.android.switchbot_lock_ext.domain.status.AsyncLockStatus
import com.seo4d696b75.android.switchbot_lock_ext.domain.status.LockState
import com.seo4d696b75.android.switchbot_lock_ext.domain.status.LockStatus
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.deviceList.component.formatString
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.home.DeviceState
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.home.HomeViewModel
import com.seo4d696b75.android.switchbot_lock_ext.ui.theme.AppTheme

@Composable
fun StatusDetailDialog(
    viewModel: HomeViewModel,
    deviceId: String,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val state = uiState.devices.firstOrNull { it.device.id == deviceId }

    if (state != null) {
        StatusDetailDialog(
            state = state,
            onDismiss = onDismiss,
            modifier = modifier,
        )
    }
}

@Composable
fun StatusDetailDialog(
    state: DeviceState,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        modifier = modifier,
        title = {
            Text(text = "Status Detail")
        },
        text = {
            ProvideTextStyle(value = MaterialTheme.typography.bodyLarge) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val device = state.device
                    Text(text = "name: ${device.name}")
                    Text(text = "id: ${device.id}")
                    Text(text = "cloud service: ${device.enableCloudService}")
                    Text(text = "hub id: ${device.hubDeviceId}")
                    Text(text = "group: ${device.group.formatString()}")

                    Spacer(modifier = Modifier.height(12.dp))

                    when (val status = state.status) {
                        is AsyncLockStatus.Data -> {
                            Text(text = "battery: ${status.data.battery}%")
                            Text(text = "version: ${status.data.version}")
                            Text(text = "state: ${status.data.state.name}")
                            Text(text = "door closed: ${status.data.isDoorClosed}")
                            Text(text = "calibrated: ${status.data.isCalibrated}")
                        }

                        else -> {}
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "OK")
            }
        },
    )
}

@Preview
@Composable
private fun StatusDetailDialogPreview() {
    AppTheme {
        StatusDetailDialog(
            state = DeviceState(
                device = LockDevice(
                    id = "device-id",
                    name = "Sample Lock",
                    enableCloudService = true,
                    hubDeviceId = "hub-device-id",
                    group = LockGroup.Disabled,
                ),
                status = AsyncLockStatus.Data(
                    data = LockStatus(
                        battery = 90,
                        version = "new",
                        state = LockState.Locked,
                        isDoorClosed = true,
                        isCalibrated = true,
                    ),
                ),
            ),
            onDismiss = { },
        )
    }
}
