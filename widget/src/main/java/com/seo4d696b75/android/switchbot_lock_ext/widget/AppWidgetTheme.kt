package com.seo4d696b75.android.switchbot_lock_ext.widget

import android.os.Build
import androidx.compose.runtime.Composable
import androidx.glance.GlanceTheme
import androidx.glance.material3.ColorProviders
import com.seo4d696b75.android.switchbot_lock_ext.ui.theme.DarkColorScheme
import com.seo4d696b75.android.switchbot_lock_ext.ui.theme.LightColorScheme

@Composable
fun AppWidgetTheme(
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit,
) {
    val colors =
        if (dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            GlanceTheme.colors
        } else {
            ColorProviders(
                light = LightColorScheme,
                dark = DarkColorScheme,
            )
        }

    GlanceTheme(colors) {
        content()
    }
}
