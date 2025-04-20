package com.seo4d696b75.android.switchbot_lock_ext.secure

import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.seo4d696b75.android.switchbot_lock_ext.ui.common.uiMessage
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.auth.NoAuthenticatorScreen
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.auth.NotAuthenticatedScreen

@Composable
fun SecureScreen(
    enabled: Boolean,
    title: String,
    description: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    if (!enabled) {
        content()
        return
    }

    val activity = LocalActivity.current as? FragmentActivity ?: return
    val viewModel: SecureViewModel = viewModel(activity)

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
    ) { }

    val owner = LocalLifecycleOwner.current
    DisposableEffect(owner, viewModel, activity) {
        val observer = object : DefaultLifecycleObserver {
            override fun onStart(owner: LifecycleOwner) {
                viewModel.authenticate(activity, title, description)
            }

            override fun onStop(owner: LifecycleOwner) {
                viewModel.lockApp()
            }
        }
        owner.lifecycle.addObserver(observer)
        onDispose {
            owner.lifecycle.removeObserver(observer)
        }
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.background
    ) {
        when (val state = uiState) {
            SecureUiState.Authenticated -> content()
            SecureUiState.NotAuthenticated -> NotAuthenticatedScreen(
                description = null,
                modifier = Modifier.fillMaxSize(),
            )

            is SecureUiState.AuthenticationError -> NotAuthenticatedScreen(
                description = uiMessage(state.message),
                modifier = Modifier.fillMaxSize(),
            )

            SecureUiState.NoAuthenticator -> NoAuthenticatorScreen(
                navigateToSetting = {
                    launcher.launchLockScreenSetting()
                },
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}

private fun ActivityResultLauncher<Intent>.launchLockScreenSetting() {
    val intent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
            putExtra(
                Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                SecureViewModel.allowedAuthenticators,
            )
        }
    } else {
        Intent(Settings.ACTION_SETTINGS)
    }
    launch(intent)
}
