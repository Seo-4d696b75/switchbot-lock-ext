package com.seo4d696b75.android.switchbot_lock_ext

import android.Manifest
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import com.seo4d696b75.android.switchbot_lock_ext.geo.GeofenceBroadcastReceiver
import com.seo4d696b75.android.switchbot_lock_ext.secure.SecureUiState
import com.seo4d696b75.android.switchbot_lock_ext.secure.SecureViewModel
import com.seo4d696b75.android.switchbot_lock_ext.secure.openLockScreenSetting
import com.seo4d696b75.android.switchbot_lock_ext.service.LockService
import com.seo4d696b75.android.switchbot_lock_ext.ui.common.uiMessage
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.auth.NoAuthenticatorScreen
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.auth.NotAuthenticatedScreen
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.main.MainScreen
import com.seo4d696b75.android.switchbot_lock_ext.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    private val viewModel: SecureViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE,
        )

        Intent(this, LockService::class.java).apply {
            putExtra(
                LockService.KEY_WHEN_STARTS,
                LockService.START_ACTIVITY_LAUNCHED,
            )
        }.also(::startService)

        setContent {
            AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                    when (val state = uiState) {
                        SecureUiState.Authenticated -> MainScreen()
                        SecureUiState.NotAuthenticated -> NotAuthenticatedScreen(
                            description = null,
                        )

                        is SecureUiState.AuthenticationError -> NotAuthenticatedScreen(
                            description = uiMessage(state.message),
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

        val geofencingClient = LocationServices.getGeofencingClient(this)
        val requestId = "geofence-test"
        val geofence = Geofence.Builder()
            .setRequestId(requestId)
            .setCircularRegion(
                35.68123,
                139.76712,
                100f,
            )
            .setExpirationDuration(3600 * 1000L)
            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
            .build()
        val geofenceRequest = GeofencingRequest.Builder().apply {
            setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
            addGeofence(geofence)
        }.build()
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            0,
            Intent(this, GeofenceBroadcastReceiver::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE,
        )
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            geofencingClient.addGeofences(geofenceRequest, pendingIntent)
                .apply {
                    addOnSuccessListener {
                        Log.d("MainActivity", "Success to add geofence")
                    }
                    addOnFailureListener {
                        Log.d("MainActivity", "Failed to add geofence $it")
                    }
                }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.authenticate(
            activity = this,
            title = getString(R.string.title_user_auth),
            subTitle = getString(R.string.description_user_auth),
        )
    }

    override fun onStop() {
        super.onStop()
        viewModel.lockApp()
    }
}
