package com.seo4d696b75.android.switchbot_lock_ext.widget

import android.content.Context
import com.seo4d696b75.android.switchbot_lock_ext.domain.widget.AppWidgetMediator
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
    override fun update() {
        AppWidgetReceiver.sendUpdateIntent(context)
    }
}

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
interface AppWidgetMediatorModule {
    @Binds
    fun bindAppWidgetMediator(impl: AppWidgetMediatorImpl): AppWidgetMediator
}
