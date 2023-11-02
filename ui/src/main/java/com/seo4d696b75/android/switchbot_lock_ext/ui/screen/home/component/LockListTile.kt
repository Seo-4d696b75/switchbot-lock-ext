package com.seo4d696b75.android.switchbot_lock_ext.ui.screen.home.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.LockDevice
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.LockGroup
import com.seo4d696b75.android.switchbot_lock_ext.domain.status.AsyncLockStatus
import com.seo4d696b75.android.switchbot_lock_ext.domain.status.LockState
import com.seo4d696b75.android.switchbot_lock_ext.domain.status.LockStatus
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.home.DeviceState
import com.seo4d696b75.android.switchbot_lock_ext.ui.theme.AppTheme

@Composable
fun LockListTile(
    state: DeviceState,
    onLockedChanged: suspend (String, Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = state.device.name,
                style = MaterialTheme.typography.titleLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            LockControlSection(
                status = state.status,
                onLockedChanged = {
                    onLockedChanged(state.device.id, it)
                },
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth(),
            )
        }
    }
}

internal class LockListTilePreviewParamProvider :
    PreviewParameterProvider<AsyncLockStatus> {
    override val values = sequenceOf(
        AsyncLockStatus.Loading,
        AsyncLockStatus.Error,
        AsyncLockStatus.Data(
            data = LockStatus(
                battery = 90,
                version = "new",
                state = LockState.Locked,
                isDoorClosed = true,
                isCalibrated = true,
            ),
        ),
        AsyncLockStatus.Data(
            data = LockStatus(
                battery = 90,
                version = "new",
                state = LockState.Unlocked,
                isDoorClosed = true,
                isCalibrated = true,
            ),
        ),
        AsyncLockStatus.Data(
            data = LockStatus(
                battery = 90,
                version = "new",
                state = LockState.Jammed,
                isDoorClosed = true,
                isCalibrated = true,
            ),
        ),
    )
}

@Preview
@Composable
private fun LockListTilePreview(
    @PreviewParameter(LockListTilePreviewParamProvider::class)
    status: AsyncLockStatus,
) {
    AppTheme {
        LockListTile(
            state = DeviceState(
                device = LockDevice(
                    id = "device-id",
                    name = "Sample Lock",
                    enableCloudService = true,
                    hubDeviceId = "hub-device-id",
                    group = LockGroup.Disabled,
                ),
                status = status,
            ),
            modifier = Modifier.width(180.dp),
            onLockedChanged = { _, _ -> },
        )
    }
}
