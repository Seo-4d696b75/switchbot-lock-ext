package com.seo4d696b75.android.switchbot_lock_ext.widget.common

import androidx.annotation.DrawableRes
import androidx.annotation.FloatRange
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.glance.ImageProvider
import androidx.glance.LocalContext
import androidx.glance.unit.ColorProvider
import com.seo4d696b75.android.switchbot_lock_ext.theme.R
import kotlin.math.roundToInt

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
): ImageProvider = remember(alpha) {
    @DrawableRes
    val id = when ((alpha * 4f).roundToInt()) {
        4 -> R.drawable.rounded_background_100
        3 -> R.drawable.rounded_background_75
        2 -> R.drawable.rounded_background_50
        1, 0 -> R.drawable.rounded_background_25
        else -> throw IllegalArgumentException()
    }
    ImageProvider(id)
}
