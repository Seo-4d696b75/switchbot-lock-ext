package com.seo4d696b75.android.switchbot_lock_ext.widget.simpleLock.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.ColorFilter
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import com.seo4d696b75.android.switchbot_lock_ext.theme.R

@Composable
fun SimpleSuccessSection(
    message: String,
    modifier: GlanceModifier = GlanceModifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            provider = ImageProvider(R.drawable.ic_check),
            contentDescription = null,
            colorFilter = ColorFilter.tint(GlanceTheme.colors.primary),
            modifier = GlanceModifier
                .defaultWeight()
                .fillMaxWidth(),
        )
        Spacer(modifier = GlanceModifier.height(2.dp))
        Text(
            text = message,
            style = TextStyle(
                color = GlanceTheme.colors.onSurface,
                fontSize = 10.sp,
            ),
        )
    }
}
