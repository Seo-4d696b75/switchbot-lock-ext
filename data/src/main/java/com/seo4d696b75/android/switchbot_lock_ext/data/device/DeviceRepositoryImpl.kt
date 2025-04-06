package com.seo4d696b75.android.switchbot_lock_ext.data.device

import com.seo4d696b75.android.switchbot_lock_ext.data.cache.cacheOf
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.DeviceRemoteRepository
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.DeviceRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

class DeviceRepositoryImpl @Inject constructor(
    private val remoteRepository: DeviceRemoteRepository,
) : DeviceRepository {

    private val cache = cacheOf {
        remoteRepository
            .getLockDevices()
            .filter { it.isControllable }
    }

    override val deviceFlow = cache()

    override suspend fun refresh() {
        cache.refresh()
    }
}

@Suppress("unused")
@Module
@InstallIn(ActivityRetainedComponent::class)
interface DeviceRepositoryModule {
    @Binds
    @ActivityRetainedScoped
    fun bindDeviceRepository(impl: DeviceRepositoryImpl): DeviceRepository
}
