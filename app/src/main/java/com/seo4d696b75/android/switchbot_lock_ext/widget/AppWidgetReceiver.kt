package com.seo4d696b75.android.switchbot_lock_ext.widget

import android.content.Context
import android.content.Intent
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.updateAll
import com.seo4d696b75.android.switchbot_lock_ext.domain.widget.LockWidgetRepository
import com.seo4d696b75.android.switchbot_lock_ext.ui.widget.LockWidget
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AppWidgetReceiver : GlanceAppWidgetReceiver() {
    @Inject
    lateinit var widgetRepository: LockWidgetRepository

    private val context = Dispatchers.IO + Job()
    private val scope = CoroutineScope(context)

    override val glanceAppWidget: GlanceAppWidget by lazy {
        LockWidget(widgetRepository, scope)
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        if (intent.action == ACTION_UPDATE_MANUAL) {
            scope.launch {
                // TODO update only state-changed widget specified by device ID
                glanceAppWidget.updateAll(context)
            }
        }
    }

    companion object {
        private const val ACTION_UPDATE_MANUAL = "action_update_manual"

        fun sendUpdateIntent(context: Context) {
            Intent(context, AppWidgetReceiver::class.java).apply {
                action = ACTION_UPDATE_MANUAL
            }.also(context::sendBroadcast)
        }
    }
}
