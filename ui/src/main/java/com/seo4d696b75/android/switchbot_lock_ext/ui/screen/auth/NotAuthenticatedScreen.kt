package com.seo4d696b75.android.switchbot_lock_ext.ui.screen.auth

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.seo4d696b75.android.switchbot_lock_ext.ui.common.AppLockedSection

@Composable
fun NotAuthenticatedScreen(
    description: String?,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        AppLockedSection(
            description = description,
        )
    }
}
