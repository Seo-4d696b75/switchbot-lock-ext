package com.seo4d696b75.android.switchbot_lock_ext.data.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import com.seo4d696b75.android.switchbot_lock_ext.data.R
import com.seo4d696b75.android.switchbot_lock_ext.domain.initialize.AppInitializer
import com.seo4d696b75.android.switchbot_lock_ext.domain.notification.AppNotificationChannel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import javax.inject.Inject

class NotificationChannelInitializer @Inject constructor() : AppInitializer {
    override suspend fun invoke(context: Context) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationChannel = NotificationChannel(
            AppNotificationChannel.LockRunner.id,
            context.getString(R.string.notification_channel_name_runner),
            NotificationManager.IMPORTANCE_DEFAULT,
        ).apply {
            description =
                context.getString(R.string.notification_channel_description_runner)
        }
        notificationManager.createNotificationChannel(notificationChannel)
    }
}

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
interface NotificationChannelInitializerModule {
    @Binds
    @IntoSet
    fun bindNotificationChannelInitializer(impl: NotificationChannelInitializer): AppInitializer
}
