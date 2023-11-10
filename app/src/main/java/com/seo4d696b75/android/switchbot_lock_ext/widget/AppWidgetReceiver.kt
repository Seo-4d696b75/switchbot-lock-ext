package com.seo4d696b75.android.switchbot_lock_ext.widget

import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import com.seo4d696b75.android.switchbot_lock_ext.ui.widget.LockWidget

class AppWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = LockWidget()
}
