package com.seo4d696b75.android.switchbot_lock_ext.widget

import com.seo4d696b75.android.switchbot_lock_ext.domain.widget.AppWidgetType
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SimpleLockWidgetConfigureActivity : AppWidgetConfigureActivity() {
    override val appWidgetType = AppWidgetType.Simple
}
