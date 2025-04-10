package com.seo4d696b75.android.switchbot_lock_ext.widget.simpleLock.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.glance.ColorFilter
import androidx.glance.ExperimentalGlanceApi
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.action.clickable
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import com.seo4d696b75.android.switchbot_lock_ext.theme.R

@OptIn(ExperimentalGlanceApi::class)
@Composable
fun SimpleLockControlButton(
    onClicked: () -> Unit,
    modifier: GlanceModifier = GlanceModifier,
) {
    Box(
        modifier = modifier
            .clickable("SimpleLockControlButton", onClicked)
            .padding(8.dp),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            provider = ImageProvider(R.drawable.round_button_background),
            contentDescription = null,
            colorFilter = ColorFilter.tint(GlanceTheme.colors.primary),
            modifier = GlanceModifier.fillMaxSize(),
        )
        Image(
            provider = ImageProvider(R.drawable.ic_lock),
            contentDescription = null,
            colorFilter = ColorFilter.tint(GlanceTheme.colors.onPrimary),
            modifier = GlanceModifier
                .fillMaxSize()
                .padding(8.dp),
        )
    }
}
