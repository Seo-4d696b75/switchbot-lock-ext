package com.seo4d696b75.android.switchbot_lock_ext.ui.screen.home.component

import android.util.Log
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.LockedState
import com.seo4d696b75.android.switchbot_lock_ext.domain.status.LockStatus
import com.seo4d696b75.android.switchbot_lock_ext.theme.R

@Composable
fun LockControlSection(
    status: LockStatus,
    onLockedChanged: (Boolean) -> Unit,
    showStatusDetail: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Crossfade(
        targetState = when (status) {
            is LockStatus.Loading -> "loading"
            is LockStatus.Error -> "error"
            is LockStatus.Data -> "data"
        },
        label = "LockControlSection",
    ) { key ->
        Log.d("LockControlSection", key)
        when (status) {
            is LockStatus.Data -> {
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
                            percent = status.state.battery,
                            modifier = Modifier.padding(8.dp),
                        )
                        IconButton(onClick = showStatusDetail) {
                            Icon(
                                Icons.Outlined.Info,
                                contentDescription = stringResource(id = R.string.label_status_detail),
                            )
                        }
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                Icons.Outlined.Settings,
                                contentDescription = stringResource(id = R.string.label_device_setting),
                            )
                        }
                    }
                    when (val state = status.state.locked) {
                        LockedState.Jammed -> {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(45.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_warn),
                                    contentDescription = null,
                                    modifier = Modifier.size(32.dp),
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(text = stringResource(id = R.string.message_locked_state_jammed))
                            }
                        }

                        LockedState.Error -> {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(45.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_warn),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.error,
                                    modifier = Modifier.size(32.dp),
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(text = stringResource(id = R.string.message_locked_state_error))
                            }
                        }

                        is LockedState.Normal -> {
                            LockToggle(
                                state = state,
                                onLockedChanged = onLockedChanged,
                            )
                        }
                    }
                }
            }

            is LockStatus.Error -> {
                Row(
                    modifier = modifier,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_warn),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(32.dp),
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = stringResource(id = R.string.message_lock_status_error))
                }
            }

            is LockStatus.Loading -> {
                Row(
                    modifier = modifier,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    CircularProgressIndicator(modifier = Modifier.size(32.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = stringResource(id = R.string.message_lock_status_loading))
                }
            }
        }
    }
}
