package com.seo4d696b75.android.switchbot_lock_ext.widget.simpleLock

import android.content.Context
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import com.seo4d696b75.android.switchbot_lock_ext.theme.AppWidgetTheme

class SimpleLockWidget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            AppWidgetTheme {

            }
        }
    }
}
