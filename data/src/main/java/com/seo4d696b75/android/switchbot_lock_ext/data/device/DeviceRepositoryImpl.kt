package com.seo4d696b75.android.switchbot_lock_ext.data.device

import com.seo4d696b75.android.switchbot_lock_ext.data.db.LockDeviceDao
import com.seo4d696b75.android.switchbot_lock_ext.data.db.LockDeviceEntity
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.DeviceRemoteRepository
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.DeviceRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

class DeviceRepositoryImpl @Inject constructor(
    private val dao: LockDeviceDao,
    private val remoteRepository: DeviceRemoteRepository,
) : DeviceRepository {
    override val deviceFlow = dao
        .getAllDevices()
        .map { list -> list.map { it.toModel() } }

    override val controlDeviceFlow = deviceFlow.map { list ->
        list.filter { it.isControllable }
    }

    override suspend fun refresh() {
        val devices = remoteRepository.getLockDevices()
        val entities = devices.map { LockDeviceEntity.fromModel(it) }
        dao.refreshDevices(entities)
    }
}

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
interface DeviceRepositoryModule {
    @Binds
    @Singleton
    fun bindDeviceRepository(impl: DeviceRepositoryImpl): DeviceRepository
}
