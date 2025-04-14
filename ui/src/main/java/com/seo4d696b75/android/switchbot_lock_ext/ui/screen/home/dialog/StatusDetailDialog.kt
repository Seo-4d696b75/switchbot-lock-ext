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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.LockedState
import com.seo4d696b75.android.switchbot_lock_ext.domain.status.LockStatus
import com.seo4d696b75.android.switchbot_lock_ext.theme.AppTheme
import com.seo4d696b75.android.switchbot_lock_ext.theme.R
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.device.component.formatString
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.home.HomeViewModel
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.home.component.LockStatusPreviewParamProvider

@Composable
fun StatusDetailDialog(
    viewModel: HomeViewModel,
    deviceId: String,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val status = uiState.devices.data?.firstOrNull { it.device.id == deviceId }

    if (status != null) {
        StatusDetailDialog(
            status = status,
            onDismiss = onDismiss,
            modifier = modifier,
        )
    }
}

@Composable
fun StatusDetailDialog(
    status: LockStatus,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        modifier = modifier,
        title = {
            Text(text = stringResource(id = R.string.title_device_status_detail))
        },
        text = {
            ProvideTextStyle(value = MaterialTheme.typography.bodyLarge) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val device = status.device
                    Text(text = "name: ${device.name}")
                    Text(text = "id: ${device.id}")
                    Text(text = "cloud service: ${device.enableCloudService}")
                    Text(text = "hub id: ${device.hubDeviceId}")
                    Text(text = "group: ${device.group.formatString()}")

                    Spacer(modifier = Modifier.height(12.dp))

                    when (status) {
                        is LockStatus.Data -> {
                            val batteryText = stringResource(
                                R.string.message_battery_percent,
                                status.state.battery,
                            )
                            Text(text = "battery: $batteryText")
                            Text(text = "version: ${status.state.version}")
                            Text(text = "state: ${status.state.locked.formatString()}")
                            Text(text = "door closed: ${status.state.isDoorClosed}")
                            Text(text = "calibrated: ${status.state.isCalibrated}")
                        }

                        else -> {}
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = stringResource(id = R.string.label_close),
                )
            }
        },
    )
}

@Composable
fun LockedState.formatString() = when (this) {
    LockedState.Error -> stringResource(id = R.string.message_locked_state_error)
    LockedState.Jammed -> stringResource(id = R.string.message_locked_state_jammed)
    is LockedState.Normal -> stringResource(
        if (isLocked) R.string.message_locked_state_locked else R.string.message_locked_state_unlocked,
    )
}

@Preview
@Composable
private fun StatusDetailDialogPreview(
    @PreviewParameter(LockStatusPreviewParamProvider::class)
    status: LockStatus,
) {
    AppTheme {
        StatusDetailDialog(
            status = status,
            onDismiss = { },
        )
    }
}
