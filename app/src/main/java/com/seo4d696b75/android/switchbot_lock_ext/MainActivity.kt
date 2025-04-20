package com.seo4d696b75.android.switchbot_lock_ext

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.FragmentActivity
import com.seo4d696b75.android.switchbot_lock_ext.secure.SecureScreen
import com.seo4d696b75.android.switchbot_lock_ext.theme.AppTheme
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.main.MainScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE,
        )

        if (savedInstanceState == null) {
            handleIntent(intent)
        }

        setContent {
            AppTheme {
                SecureScreen(
                    modifier = Modifier.fillMaxSize(),
                    enabled = true,
                    title = stringResource(R.string.title_user_auth),
                    description = stringResource(R.string.description_user_auth),
                ) {
                    MainScreen()
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent?) {
        intent ?: return
        if (intent.getBooleanExtra("inapp_configuration_completed", false)) {
            finish()
        }
    }
}
