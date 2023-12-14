package com.seo4d696b75.android.switchbot_lock_ext.widget.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.seo4d696b75.android.switchbot_lock_ext.theme.R

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
            )
            .clickable("LockControlButton-$label", onClicked)
            .padding(2.dp),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = GlanceModifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                provider = icon,
                contentDescription = label,
                colorFilter = ColorFilter.tint(GlanceTheme.colors.onPrimary),
                modifier = GlanceModifier.size(30.dp),
            )
            Text(
                text = label,
                style = TextStyle(
                    color = GlanceTheme.colors.onPrimary,
                    fontSize = 10.sp,
                ),
            )
        }
    }
}
