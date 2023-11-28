package com.seo4d696b75.android.switchbot_lock_ext.ui.widget.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.glance.ColorFilter
import androidx.glance.ExperimentalGlanceApi
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.action.clickable
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.size
import androidx.glance.unit.ColorProvider
import com.seo4d696b75.android.switchbot_lock_ext.ui.R

@OptIn(ExperimentalGlanceApi::class)
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
            .size(46.dp)
            .background(
                ImageProvider(
                    id = R.drawable.lock_button_background,
                    tintColor = color,
                )
            ),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            provider = icon,
            contentDescription = label,
            colorFilter = ColorFilter.tint(GlanceTheme.colors.onPrimary),
            modifier = GlanceModifier
                .size(40.dp)
                .clickable("LockControlButton-$label", onClicked),
        )
    }
}
