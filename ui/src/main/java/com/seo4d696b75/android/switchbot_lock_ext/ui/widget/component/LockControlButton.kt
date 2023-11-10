package com.seo4d696b75.android.switchbot_lock_ext.ui.widget.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.glance.ColorFilter
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.action.clickable
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Spacer
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.layout.wrapContentSize
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import com.seo4d696b75.android.switchbot_lock_ext.ui.R

@Composable
fun LockControlButton(
    isLocked: Boolean,
    onLockedChanged: (Boolean) -> Unit,
    modifier: GlanceModifier = GlanceModifier,
) {
    val contentColor =
        if (isLocked) GlanceTheme.colors.primary else GlanceTheme.colors.secondary

    Column(
        modifier = modifier
            .wrapContentSize()
            .padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = GlanceModifier
                .size(40.dp)
                .clickable { onLockedChanged(!isLocked) }
                .background(
                    ImageProvider(
                        id = R.drawable.lock_button_background,
                        tintColor = contentColor,
                    )
                ),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                provider = ImageProvider(if (isLocked) R.drawable.ic_lock else R.drawable.ic_unlock),
                contentDescription = if (isLocked) "unlock" else "lock",
                colorFilter = ColorFilter.tint(GlanceTheme.colors.onPrimary),
                modifier = GlanceModifier.size(36.dp),
            )
        }
        Spacer(modifier = GlanceModifier.height(4.dp))
        Text(
            text = if (isLocked) "Unlock" else "Lock",
            style = TextStyle(
                color = contentColor,
                fontWeight = FontWeight.Bold,
            ),
        )
    }
}
