package com.seo4d696b75.android.switchbot_lock_ext.secure

import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.activity.result.ActivityResultLauncher

fun ActivityResultLauncher<Intent>.launchLockScreenSetting() {
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
