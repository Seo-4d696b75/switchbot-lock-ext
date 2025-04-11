package com.seo4d696b75.android.switchbot_lock_ext.widget.lock.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.ImageProvider
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.padding
import androidx.glance.layout.width
import androidx.glance.preview.ExperimentalGlancePreviewApi
import androidx.glance.preview.Preview
import com.seo4d696b75.android.switchbot_lock_ext.theme.AppWidgetTheme
import com.seo4d696b75.android.switchbot_lock_ext.theme.R
import com.seo4d696b75.android.switchbot_lock_ext.widget.common.glanceString

@Composable
fun LockControlButtonSection(
    onClicked: (Boolean) -> Unit,
    modifier: GlanceModifier = GlanceModifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                vertical = 12.dp,
                horizontal = 16.dp,
            ),
    ) {
        LockControlButton(
            label = glanceString(id = R.string.label_unlock),
            icon = ImageProvider(R.drawable.ic_unlock),
            color = GlanceTheme.colors.secondary,
            onClicked = { onClicked(false) },
            modifier = GlanceModifier.defaultWeight(),
        )
        Spacer(modifier = GlanceModifier.width(12.dp))
        LockControlButton(
            label = glanceString(id = R.string.label_lock),
            icon = ImageProvider(R.drawable.ic_lock),
            color = GlanceTheme.colors.primary,
            onClicked = { onClicked(true) },
            modifier = GlanceModifier.defaultWeight(),
        )
    }
}

@OptIn(ExperimentalGlancePreviewApi::class)
@Preview
@Composable
private fun LockControlButtonSectionPreview() {
    AppWidgetTheme {
        LockControlButtonSection(
            onClicked = {},
        )
    }
}
