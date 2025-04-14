package com.seo4d696b75.android.switchbot_lock_ext.domain.widget

interface AppWidgetMediator {
    suspend fun getConfiguration(
        appWidgetId: Int,
        type: AppWidgetType,
    ): AppWidgetConfiguration?

    suspend fun configure(
        appWidgetId: Int,
        configuration: AppWidgetConfiguration,
    )

    suspend fun addWidget(
        type: AppWidgetType,
        deviceId: String,
    )
}
