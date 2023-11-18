package com.seo4d696b75.android.switchbot_lock_ext.widget

import android.appwidget.AppWidgetManager
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.seo4d696b75.android.switchbot_lock_ext.domain.widget.AppWidgetMediator
import com.seo4d696b75.android.switchbot_lock_ext.secure.SecureUiState
import com.seo4d696b75.android.switchbot_lock_ext.secure.SecureViewModel
import com.seo4d696b75.android.switchbot_lock_ext.secure.openLockScreenSetting
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.auth.NoAuthenticatorScreen
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.auth.NotAuthenticatedScreen
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.widgetConfiguration.LockWidgetConfigurationScreen
import com.seo4d696b75.android.switchbot_lock_ext.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AppWidgetConfigureActivity : FragmentActivity() {

    private val viewModel: SecureViewModel by viewModels()

    @Inject
    lateinit var widgetMediator: AppWidgetMediator

    private val appWidgetId by lazy {
        intent?.extras?.getInt(
            AppWidgetManager.EXTRA_APPWIDGET_ID,
            AppWidgetManager.INVALID_APPWIDGET_ID,
        ) ?: AppWidgetManager.INVALID_APPWIDGET_ID
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setResult(
            RESULT_CANCELED,
            Intent().apply {
                putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            },
        )
        if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish()
        }

        setContent {
            AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                    when (val state = uiState) {
                        SecureUiState.Authenticated -> LockWidgetConfigurationScreen(
                            onCompleted = ::onConfigurationCompleted,
                        )

                        SecureUiState.NotAuthenticated -> NotAuthenticatedScreen(
                            description = null,
                        )

                        is SecureUiState.AuthenticationError -> NotAuthenticatedScreen(
                            description = state.message,
                        )

                        SecureUiState.NoAuthenticator -> NoAuthenticatorScreen(
                            navigateToSetting = {
                                openLockScreenSetting {}
                            },
                        )
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launch {
            // FIXME if no delay, authentication will be cancelled without user interaction
            delay(500L)
            viewModel.authenticate(this@AppWidgetConfigureActivity)
        }
    }

    override fun onStop() {
        super.onStop()
        viewModel.lockApp()
    }

    private fun onConfigurationCompleted(deviceId: String) {
        lifecycleScope.launch {
            widgetMediator.initializeLockWidgetState(
                appWidgetId,
                deviceId,
            )
            setResult(
                RESULT_OK,
                Intent().apply {
                    putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
                },
            )
            finish()
        }
    }
}