package com.seo4d696b75.android.switchbot_lock_ext.ui.screen.device.page

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.seo4d696b75.android.switchbot_lock_ext.ui.common.NoUserSection
import com.seo4d696b75.android.switchbot_lock_ext.ui.theme.AppTheme

@Composable
fun NoUserDevicePage(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        NoUserSection()
    }
}

@Preview
@Composable
private fun NoUserDevicePagePreview(){
    AppTheme {
        Surface {
            NoUserDevicePage()
        }
    }
}
