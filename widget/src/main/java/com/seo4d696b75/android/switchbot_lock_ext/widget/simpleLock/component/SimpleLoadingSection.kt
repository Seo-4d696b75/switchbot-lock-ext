package com.seo4d696b75.android.switchbot_lock_ext.widget.simpleLock.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.appwidget.CircularProgressIndicator
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.text.Text
import androidx.glance.text.TextStyle

@Composable
fun SimpleLoadingSection(
    message: String,
    modifier: GlanceModifier = GlanceModifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CircularProgressIndicator(
            modifier = GlanceModifier.defaultWeight(),
            color = GlanceTheme.colors.primary,
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
