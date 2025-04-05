package com.seo4d696b75.android.switchbot_lock_ext.data.device

import com.seo4d696b75.android.switchbot_lock_ext.domain.device.DeviceRemoteRepository
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.DeviceRepository
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.LockDevice
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class DeviceRepositoryImpl @Inject constructor(
    private val remoteRepository: DeviceRemoteRepository,
) : DeviceRepository {

    private val cache = MutableStateFlow<List<LockDevice>?>(null)

    override val deviceFlow = cache.asStateFlow()

    override suspend fun refresh() {
        cache.update { null }
        val devices = remoteRepository
            .getLockDevices()
            .filter { it.isControllable }
        cache.update { devices }
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
