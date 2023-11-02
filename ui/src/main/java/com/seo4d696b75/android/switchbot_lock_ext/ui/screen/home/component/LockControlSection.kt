package com.seo4d696b75.android.switchbot_lock_ext.ui.screen.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.seo4d696b75.android.switchbot_lock_ext.domain.status.AsyncLockStatus
import com.seo4d696b75.android.switchbot_lock_ext.domain.status.LockState

@Composable
fun LockControlSection(
    status: AsyncLockStatus,
    onLockedChanged: suspend (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (status) {
        is AsyncLockStatus.Data -> {
            Column(
                modifier = modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    BatterySection(
                        percent = status.data.battery,
                        modifier = Modifier.padding(8.dp),
                    )
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            Icons.Outlined.Info,
                            contentDescription = "status details",
                        )
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            Icons.Outlined.Settings,
                            contentDescription = "setting",
                        )
                    }
                }
                if (status.data.state == LockState.Jammed) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        Icon(Icons.Outlined.Warning, contentDescription = null)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "Jammed")
                    }
                } else {
                    LockToggle(
                        isLocked = status.data.state == LockState.Locked,
                        onLockedChanged = onLockedChanged,
                    )
                }
            }
        }

        AsyncLockStatus.Error -> {
            Row(
                modifier = modifier,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                Icon(Icons.Outlined.Warning, contentDescription = null)
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "API error")
            }
        }

        AsyncLockStatus.Loading -> {
            Row(
                modifier = modifier,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                CircularProgressIndicator(modifier = Modifier.size(40.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Loading")
            }
        }
    }
}
