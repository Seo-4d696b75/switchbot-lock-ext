package com.seo4d696b75.android.switchbot_lock_ext.ui.screen.deviceList.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.LockDevice
import com.seo4d696b75.android.switchbot_lock_ext.ui.common.InfoSection
import kotlinx.collections.immutable.ImmutableList

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DeviceListSection(
    devices: ImmutableList<LockDevice>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        stickyHeader {
            InfoSection(
                description = "Not all devices are controllable.\n- Cloud service MUST be enabled\n- Not grouped with others, or is master of grouped devices",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
            )
        }
        items(
            devices,
            key = { it.id },
        ) {
            DeviceListItem(
                device = it,
            )
        }
    }
}
