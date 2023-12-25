package com.seo4d696b75.android.switchbot_lock_ext.widget

import android.appwidget.AppWidgetManager
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.seo4d696b75.android.switchbot_lock_ext.R
import com.seo4d696b75.android.switchbot_lock_ext.domain.widget.AppWidgetType
import com.seo4d696b75.android.switchbot_lock_ext.secure.SecureUiState
import com.seo4d696b75.android.switchbot_lock_ext.secure.SecureViewModel
import com.seo4d696b75.android.switchbot_lock_ext.secure.launchLockScreenSetting
import com.seo4d696b75.android.switchbot_lock_ext.theme.AppTheme
import com.seo4d696b75.android.switchbot_lock_ext.ui.common.uiMessage
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.auth.NoAuthenticatorScreen
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.auth.NotAuthenticatedScreen
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.widgetConfiguration.LockWidgetConfigurationScreen
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.widgetConfiguration.WidgetConfigurationViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
abstract class AppWidgetConfigureActivity : FragmentActivity() {

    private val secureViewModel: SecureViewModel by viewModels()

    abstract val appWidgetType: AppWidgetType

    @Inject
    lateinit var configureViewModelFactory: WidgetConfigurationViewModel.Factory

    private val appWidgetId by lazy {
        intent?.extras?.getInt(
            AppWidgetManager.EXTRA_APPWIDGET_ID,
            AppWidgetManager.INVALID_APPWIDGET_ID,
        ) ?: AppWidgetManager.INVALID_APPWIDGET_ID
    }

    private val lockScreenSettingLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
    ) {}

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
                    val uiState by secureViewModel.uiState.collectAsStateWithLifecycle()
                    when (val state = uiState) {
                        SecureUiState.Authenticated -> LockWidgetConfigurationScreen(
                            onCompleted = {
                                setResult(
                                    RESULT_OK,
                                    Intent().apply {
                                        putExtra(
                                            AppWidgetManager.EXTRA_APPWIDGET_ID,
                                            appWidgetId
                                        )
                                    },
                                )
                                finish()
                            },
                            viewModel = viewModel {
                                configureViewModelFactory.create(
                                    createSavedStateHandle(),
                                    appWidgetType,
                                )
                            },
                        )

                        SecureUiState.NotAuthenticated -> NotAuthenticatedScreen(
                            description = null,
                        )

                        is SecureUiState.AuthenticationError -> NotAuthenticatedScreen(
                            description = uiMessage(state.message),
                        )

                        SecureUiState.NoAuthenticator -> NoAuthenticatorScreen(
                            navigateToSetting = {
                                lockScreenSettingLauncher.launchLockScreenSetting()
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
            secureViewModel.authenticate(
                activity = this@AppWidgetConfigureActivity,
                title = getString(R.string.title_user_auth),
                subTitle = getString(R.string.description_user_auth_widget_configuration),
            )
        }
    }

    override fun onStop() {
        super.onStop()
        secureViewModel.lockApp()
    }
}
