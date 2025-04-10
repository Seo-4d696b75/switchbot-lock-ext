package com.seo4d696b75.android.switchbot_lock_ext.widget.common

import android.graphics.Canvas
import android.graphics.PorterDuff
import androidx.annotation.FloatRange
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.toArgb
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.glance.GlanceTheme
import androidx.glance.ImageProvider
import androidx.glance.LocalContext
import androidx.glance.unit.ColorProvider
import com.seo4d696b75.android.switchbot_lock_ext.theme.R

@Composable
fun glanceString(@StringRes id: Int): String {
    return LocalContext.current.getString(id)
}

@Composable
fun ColorProvider.copy(
    @FloatRange(from = 0.0, to = 1.0)
    alpha: Float? = null,
    @FloatRange(from = 0.0, to = 1.0)
    red: Float? = null,
    @FloatRange(from = 0.0, to = 1.0)
    green: Float? = null,
    @FloatRange(from = 0.0, to = 1.0)
    blue: Float? = null,
): ColorProvider {
    val color = this.getColor(LocalContext.current)
    val modified = color.copy(
        red = red ?: color.red,
        green = green ?: color.green,
        blue = blue ?: color.blue,
        alpha = alpha ?: color.alpha,
    )
    return ColorProvider(modified)
}

@Composable
fun rememberWidgetBackground(
    @FloatRange(from = 0.0, to = 1.0)
    alpha: Float = 1f,
    width: Int = 256,
    height: Int = 256,
): ImageProvider {
    val context = LocalContext.current
    val c = GlanceTheme.colors.surface.getColor(context)
    val bitmap = remember(context, c) {
        requireNotNull(
            ContextCompat.getDrawable(
                context,
                R.drawable.lock_widget_background,
            ),
        ).toBitmap(width, height).also {
            Canvas(it).apply {
                this.drawColor(
                    c.copy(alpha = alpha).toArgb(),
                    PorterDuff.Mode.SRC_IN,
                )
            }
        }
    }
    return ImageProvider(bitmap)
}
