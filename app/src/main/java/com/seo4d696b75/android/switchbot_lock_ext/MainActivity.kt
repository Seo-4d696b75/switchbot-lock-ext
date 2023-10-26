package com.seo4d696b75.android.switchbot_lock_ext

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.DeviceRepository
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.main.MainScreen
import com.seo4d696b75.android.switchbot_lock_ext.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var repository: DeviceRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                val status = repository.getLockDeviceStatus("E8893CF06602")
                Log.d("Api", "$status")
                val res = repository.getLockDevices()
                Log.d("Api", "$res")
            }
        }
    }
}
