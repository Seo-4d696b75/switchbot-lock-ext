package com.seo4d696b75.android.switchbot_lock_ext.widget

import com.seo4d696b75.android.switchbot_lock_ext.domain.widget.AppWidgetType
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

abstract class SimpleLockWidgetConfigureActivity :
    AppWidgetConfigureActivity() {
    override val appWidgetType = AppWidgetType.Simple

    @AndroidEntryPoint
    class Organic : SimpleLockWidgetConfigureActivity() {
        override val initialDeviceId = null
    }

    @AndroidEntryPoint
    class InApp : SimpleLockWidgetConfigureActivity() {

        @Inject
        lateinit var callbackHolder: InAppWidgetCallbackHolder

        override val initialDeviceId: String? by lazy {
            callbackHolder.pop()
                ?: throw RuntimeException("in-app widget configuration is required, but callback not found.")
        }
    }
}
