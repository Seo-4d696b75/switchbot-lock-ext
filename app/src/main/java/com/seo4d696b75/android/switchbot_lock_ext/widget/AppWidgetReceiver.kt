package com.seo4d696b75.android.switchbot_lock_ext.widget

import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import com.seo4d696b75.android.switchbot_lock_ext.domain.widget.AppWidgetMediator
import com.seo4d696b75.android.switchbot_lock_ext.ui.widget.LockWidget
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AppWidgetReceiver : GlanceAppWidgetReceiver() {

    @Inject
    lateinit var widgetMediator: AppWidgetMediator

    override val glanceAppWidget: GlanceAppWidget by lazy {
        LockWidget(widgetMediator)
    }
}
