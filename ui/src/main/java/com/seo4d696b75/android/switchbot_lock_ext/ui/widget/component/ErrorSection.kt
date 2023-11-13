package com.seo4d696b75.android.switchbot_lock_ext.ui.widget.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.glance.ColorFilter
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.layout.Alignment
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.height
import androidx.glance.layout.size
import androidx.glance.layout.wrapContentSize
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import com.seo4d696b75.android.switchbot_lock_ext.ui.R

@Composable
fun ErrorSection(
    message: String,
    modifier: GlanceModifier = GlanceModifier,
) {
    Row(
        modifier = modifier.wrapContentSize(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            provider = ImageProvider(R.drawable.ic_error),
            contentDescription = null,
            colorFilter = ColorFilter.tint(GlanceTheme.colors.error),
            modifier = GlanceModifier.size(40.dp)
        )
        Spacer(modifier = GlanceModifier.height(12.dp))
        Text(
            text = message,
            style = TextStyle(
                color = GlanceTheme.colors.onSurface,
            ),
        )
    }
}
