package com.seo4d696b75.android.switchbot_lock_ext

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.seo4d696b75.android.switchbot_lock_ext.domain.initialize.AppInitializer
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class MainApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    @Inject
    lateinit var appInitializers: MutableSet<AppInitializer>

    override fun getWorkManagerConfiguration() =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    override fun onCreate() {
        super.onCreate()
        val initializeJob = Job()
        val scope = CoroutineScope(Dispatchers.Default + initializeJob)
        scope.launch {
            appInitializers.map {
                launch { it(this@MainApplication) }
            }.joinAll()
        }
    }

}
