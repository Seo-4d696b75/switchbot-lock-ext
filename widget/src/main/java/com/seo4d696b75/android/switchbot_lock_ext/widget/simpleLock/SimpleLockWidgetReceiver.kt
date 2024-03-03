package com.seo4d696b75.android.switchbot_lock_ext.widget.simpleLock

import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver

class SimpleLockWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget by lazy {
        SimpleLockWidget()
    }
}
