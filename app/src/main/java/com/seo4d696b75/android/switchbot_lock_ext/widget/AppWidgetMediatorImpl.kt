package com.seo4d696b75.android.switchbot_lock_ext.widget

import android.app.ActivityOptions
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.os.bundleOf
import androidx.glance.appwidget.GlanceAppWidgetManager
import com.seo4d696b75.android.switchbot_lock_ext.domain.widget.AppWidgetConfiguration
import com.seo4d696b75.android.switchbot_lock_ext.domain.widget.AppWidgetMediator
import com.seo4d696b75.android.switchbot_lock_ext.domain.widget.AppWidgetType
import com.seo4d696b75.android.switchbot_lock_ext.widget.lock.LockWidget
import com.seo4d696b75.android.switchbot_lock_ext.widget.lock.LockWidgetReceiver
import com.seo4d696b75.android.switchbot_lock_ext.widget.simpleLock.SimpleLockWidget
import com.seo4d696b75.android.switchbot_lock_ext.widget.simpleLock.SimpleLockWidgetReceiver
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

class AppWidgetMediatorImpl @Inject constructor(
    @ApplicationContext
    private val context: Context,
    private val callbackHolder: InAppWidgetCallbackHolder,
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

    override suspend fun addWidget(
        type: AppWidgetType,
        deviceId: String,
    ) {
        val manager = AppWidgetManager.getInstance(context)
        if (manager.isRequestPinAppWidgetSupported) {
            val receiverClass = when (type) {
                AppWidgetType.Standard -> LockWidgetReceiver::class
                AppWidgetType.Simple -> SimpleLockWidgetReceiver::class
            }
            val provider = ComponentName(context, receiverClass.java)
            val configActivity = when (type) {
                AppWidgetType.Standard -> LockWidgetConfigureActivity.InApp::class
                AppWidgetType.Simple -> SimpleLockWidgetConfigureActivity.InApp::class
            }
            // FIXME ウィジェットをドラッグで位置調整しながら追加すると設定Activityが起動しない
            // アプリがバックグランドになってAndroid14以降の制約に抵触する？
            // https://developer.android.com/guide/components/activities/background-starts?hl=ja#opt-in-required
            // PendingIntent.send はアプリ外から呼ばれるので制御できない
            val options =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                    ActivityOptions.makeBasic()
                        .setPendingIntentCreatorBackgroundActivityStartMode(
                            ActivityOptions.MODE_BACKGROUND_ACTIVITY_START_ALLOWED
                        )
                        .toBundle()
                } else {
                    bundleOf(
                        "android.activity.pendingIntentCreatorBackgroundActivityStartMode" to 1
                    )
                }
            val callback = PendingIntent.getActivity(
                context,
                deviceId.hashCode(),
                Intent(context, configActivity.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE,
                options,
            )
            val result = manager.requestPinAppWidget(provider, null, callback)
            if (result) {
                callbackHolder.enqueue(deviceId)
            }
        } else {
            throw RuntimeException("requestPinAppWidget not supported")
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
