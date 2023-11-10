package com.seo4d696b75.android.switchbot_lock_ext.widget

import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.updateAll
import com.seo4d696b75.android.switchbot_lock_ext.domain.status.LockStatusRepository
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
    lateinit var statusRepository: LockStatusRepository

    override val glanceAppWidget: GlanceAppWidget by lazy {
        LockWidget(statusRepository)
    }

    private val context = Dispatchers.IO + Job()
    private val scope = CoroutineScope(context)

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        scope.launch {
            glanceAppWidget.updateAll(context)
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        if (intent.action == ACTION_UPDATE_MANUAL) {
            scope.launch {
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
