package com.seo4d696b75.android.switchbot_lock_ext.data.automation

import com.seo4d696b75.android.switchbot_lock_ext.data.db.LockAutomationDao
import com.seo4d696b75.android.switchbot_lock_ext.data.db.LockAutomationEntity
import com.seo4d696b75.android.switchbot_lock_ext.domain.automation.AutomationRepository
import com.seo4d696b75.android.switchbot_lock_ext.domain.automation.LockAutomation
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.DeviceRepository
import com.seo4d696b75.android.switchbot_lock_ext.domain.geo.GeofenceRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.combine
import java.util.UUID
import javax.inject.Inject

class AutomationRepositoryImpl @Inject constructor(
    private val dao: LockAutomationDao,
    deviceRepository: DeviceRepository,
    private val geofenceRepository: GeofenceRepository,
) : AutomationRepository {

    override val automationFlow = combine(
        dao.getAll(),
        deviceRepository.controlDeviceFlow,
        geofenceRepository.geofenceFlow,
    ) { automations, devices, geofences ->
        automations.map { automation ->
            val device = devices.first { it.id == automation.deviceId }
            val geofence = geofences.first { it.id == automation.geofenceId }
            automation.toModel(device, geofence)
        }
    }

    override suspend fun addAutomation(automation: LockAutomation): String {
        val id = UUID.randomUUID().toString()
        val geofenceId = geofenceRepository.addGeofence(automation.geofence)
        val entity = LockAutomationEntity.fromModel(automation).copy(
            id = id,
            geofenceId = geofenceId,
        )
        dao.add(entity)
        return id
    }

    override suspend fun updateAutomation(automation: LockAutomation) {
        geofenceRepository.updateGeofence(automation.geofence)
        val entity = LockAutomationEntity.fromModel(automation)
        dao.update(entity)
    }

    override suspend fun removeAutomation(automation: LockAutomation) {
        geofenceRepository.removeGeofence(automation.geofence)
        dao.remove(listOf(automation.id))
    }
}

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
interface AutomationRepositoryModule {
    @Binds
    fun bindAutomationRepository(impl: AutomationRepositoryImpl): AutomationRepository
}
