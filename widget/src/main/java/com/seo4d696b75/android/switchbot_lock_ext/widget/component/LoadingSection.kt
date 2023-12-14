package com.seo4d696b75.android.switchbot_lock_ext.widget.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.appwidget.CircularProgressIndicator
import androidx.glance.layout.Alignment
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.size
import androidx.glance.layout.width
import androidx.glance.layout.wrapContentSize
import androidx.glance.text.Text
import androidx.glance.text.TextStyle

@Composable
fun LoadingSection(
    message: String,
    modifier: GlanceModifier = GlanceModifier,
) {
    Row(
        modifier = modifier.wrapContentSize(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CircularProgressIndicator(
            modifier = GlanceModifier.size(40.dp),
            color = GlanceTheme.colors.primary,
        )
        Spacer(modifier = GlanceModifier.width(8.dp))
        Text(
            text = message,
            style = TextStyle(
                color = GlanceTheme.colors.onSurface,
            ),
        )
    }
}
