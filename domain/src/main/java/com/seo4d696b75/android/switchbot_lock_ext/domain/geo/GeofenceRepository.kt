package com.seo4d696b75.android.switchbot_lock_ext.domain.geo

import kotlinx.coroutines.flow.Flow

interface GeofenceRepository {
    val geofenceFlow: Flow<List<LockGeofence>>
    suspend fun addGeofence(geofence: LockGeofence): String

    suspend fun updateGeofence(geofence: LockGeofence)
    suspend fun removeGeofence(geofence: LockGeofence)
}
