package com.seo4d696b75.android.switchbot_lock_ext.widget

import android.appwidget.AppWidgetManager
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.FragmentActivity
import com.seo4d696b75.android.switchbot_lock_ext.MainActivity
import com.seo4d696b75.android.switchbot_lock_ext.R
import com.seo4d696b75.android.switchbot_lock_ext.domain.widget.AppWidgetType
import com.seo4d696b75.android.switchbot_lock_ext.secure.SecureScreen
import com.seo4d696b75.android.switchbot_lock_ext.theme.AppTheme
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.configure.ConfigurationScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class AppWidgetConfigureActivity : FragmentActivity() {

    abstract val appWidgetType: AppWidgetType

    abstract val initialDeviceId: String?

    abstract val isScreenLockRequired: Boolean

    private val appWidgetId by lazy {
        intent?.extras?.getInt(
            AppWidgetManager.EXTRA_APPWIDGET_ID,
            AppWidgetManager.INVALID_APPWIDGET_ID,
        ) ?: AppWidgetManager.INVALID_APPWIDGET_ID
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            throw IllegalArgumentException()
        }
        setResult(
            RESULT_CANCELED,
            Intent().apply {
                putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            },
        )

        // TODO enable edge2edge

        setContent {
            AppTheme {
                SecureScreen(
                    modifier = Modifier.fillMaxSize(),
                    enabled = isScreenLockRequired,
                    title = stringResource(R.string.title_user_auth),
                    description = stringResource(R.string.description_user_auth_widget_configuration),
                ) {
                    ConfigurationScreen(
                        appWidgetType = appWidgetType,
                        appWidgetId = appWidgetId,
                        initialDeviceId = initialDeviceId,
                        onCompleted = ::onComplete,
                    )
                }
            }
        }
    }

    private fun onComplete() {
        setResult(
            RESULT_OK,
            Intent().apply {
                putExtra(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    appWidgetId
                )
            },
        )
        if (initialDeviceId == null) {
            finish()
        } else {
            val intent = Intent(
                this,
                MainActivity::class.java
            ).apply {
                putExtra("inapp_configuration_completed", true)
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }
            startActivity(intent)
        }
    }
}
