package com.seo4d696b75.android.switchbot_lock_ext.widget

import android.content.Context
import androidx.glance.appwidget.GlanceAppWidgetManager
import com.seo4d696b75.android.switchbot_lock_ext.domain.widget.AppWidgetConfiguration
import com.seo4d696b75.android.switchbot_lock_ext.domain.widget.AppWidgetMediator
import com.seo4d696b75.android.switchbot_lock_ext.domain.widget.AppWidgetType
import com.seo4d696b75.android.switchbot_lock_ext.widget.lock.LockWidget
import com.seo4d696b75.android.switchbot_lock_ext.widget.simpleLock.SimpleLockWidget
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

class AppWidgetMediatorImpl @Inject constructor(
    @ApplicationContext
    private val context: Context,
) : AppWidgetMediator {

    private val glanceAppWidgetManager = GlanceAppWidgetManager(context)

    override suspend fun getConfiguration(
        appWidgetId: Int,
        type: AppWidgetType
    ): AppWidgetConfiguration? {
        val glanceId = glanceAppWidgetManager.getGlanceIdBy(appWidgetId)
        return when (type) {
            AppWidgetType.Standard ->
                LockWidget().getConfiguration(context, glanceId)

            AppWidgetType.Simple ->
                SimpleLockWidget().getConfiguration(context, glanceId)
        }
    }

    override suspend fun configure(
        appWidgetId: Int,
        configuration: AppWidgetConfiguration
    ) {
        val glanceId = glanceAppWidgetManager.getGlanceIdBy(appWidgetId)
        when (configuration.type) {
            AppWidgetType.Standard -> {
                LockWidget().configure(
                    context,
                    glanceId,
                    configuration.deviceId,
                    configuration.deviceName,
                    configuration.opacity,
                )
            }

            AppWidgetType.Simple -> {
                SimpleLockWidget().configure(
                    context,
                    glanceId,
                    configuration.deviceId,
                    configuration.deviceName,
                    configuration.opacity,
                )
            }
        }
    }
}

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
interface AppWidgetMediatorModule {
    @Binds
    fun bindAppWidgetMediator(impl: AppWidgetMediatorImpl): AppWidgetMediator
}
