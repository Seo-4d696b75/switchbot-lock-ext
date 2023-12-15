package com.seo4d696b75.android.switchbot_lock_ext.ui.widget.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.width
import com.seo4d696b75.android.switchbot_lock_ext.ui.R

@Composable
fun LockControlButtonSection(
    onClicked: (Boolean) -> Unit,
    modifier: GlanceModifier = GlanceModifier,
) {
    Row(
        modifier = modifier,
    ) {
        LockControlButton(
            label = stringResource(id = R.string.label_unlock),
            icon = ImageProvider(R.drawable.ic_unlock),
            color = GlanceTheme.colors.secondary,
            onClicked = { onClicked(false) },
        )
        Spacer(modifier = GlanceModifier.width(12.dp))
        LockControlButton(
            label = stringResource(id = R.string.label_lock),
            icon = ImageProvider(R.drawable.ic_lock),
            color = GlanceTheme.colors.primary,
            onClicked = { onClicked(true) },
        )
    }
}