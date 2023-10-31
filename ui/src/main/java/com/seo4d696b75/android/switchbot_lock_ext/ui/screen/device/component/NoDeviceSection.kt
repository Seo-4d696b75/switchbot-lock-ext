package com.seo4d696b75.android.switchbot_lock_ext.ui.screen.device.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.seo4d696b75.android.switchbot_lock_ext.ui.R

@Composable
fun NoDeviceSection(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_no_lock),
            contentDescription = null,
            modifier = Modifier.size(160.dp),
            colorFilter = ColorFilter.tint(Color.Gray),
        )
        Text(
            text = "No lock devices",
            style = MaterialTheme.typography.h6,
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Your devices not found? Please refresh device list for the latest data.",
            style = MaterialTheme.typography.body2,
            modifier = Modifier.padding(horizontal = 32.dp),
        )
    }
}
