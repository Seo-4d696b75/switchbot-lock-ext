package com.seo4d696b75.android.switchbot_lock_ext.data.device

import com.seo4d696b75.android.switchbot_lock_ext.data.db.LockDeviceDao
import com.seo4d696b75.android.switchbot_lock_ext.data.db.LockDeviceEntity
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.DeviceRemoteRepository
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.DeviceRepository
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.LockGroup
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DeviceRepositoryImpl @Inject constructor(
    private val dao: LockDeviceDao,
    private val remoteRepository: DeviceRemoteRepository,
) : DeviceRepository {
    override val deviceFlow = dao
        .getAllDevices()
        .map { list -> list.map { it.toModel() } }

    override val controlDeviceFlow = deviceFlow.map { list ->
        list.filter {
            when (val group = it.group) {
                LockGroup.Disabled -> true
                is LockGroup.Enabled -> group.isMaster
            }
        }
    }

    override suspend fun refresh() {
        val devices = remoteRepository.getLockDevices()
        val entities = devices.map { LockDeviceEntity.fromModel(it) }
        dao.refreshDevices(entities)
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
