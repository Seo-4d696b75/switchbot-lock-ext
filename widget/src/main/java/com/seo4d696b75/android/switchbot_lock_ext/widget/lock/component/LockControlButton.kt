package com.seo4d696b75.android.switchbot_lock_ext.widget.lock.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.glance.ColorFilter
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.action.clickable
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.fillMaxHeight
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.unit.ColorProvider
import com.seo4d696b75.android.switchbot_lock_ext.theme.R

@Composable
fun LockControlButton(
    label: String,
    icon: ImageProvider,
    color: ColorProvider,
    onClicked: () -> Unit,
    modifier: GlanceModifier = GlanceModifier,
) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .clickable(onClicked),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            provider = ImageProvider(R.drawable.rounded_background_100),
            contentDescription = null,
            colorFilter = ColorFilter.tint(color),
            modifier = GlanceModifier.fillMaxSize(),
        )
        Image(
            provider = icon,
            contentDescription = label,
            colorFilter = ColorFilter.tint(GlanceTheme.colors.onPrimary),
            modifier = GlanceModifier
                .fillMaxSize()
                .padding(4.dp),
        )
    }
}
