package com.seo4d696b75.android.switchbot_lock_ext.ui.screen.widgetConfiguration

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.seo4d696b75.android.switchbot_lock_ext.ui.common.PrimaryButton

@Composable
fun LockWidgetConfigurationScreen(
    onCompleted: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        PrimaryButton(
            label = "Add widget",
            onClick = {
                onCompleted("E8893CF06602")
            },
        )
    }
}
