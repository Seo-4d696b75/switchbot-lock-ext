package com.seo4d696b75.android.switchbot_lock_ext.domain.geo

import kotlinx.coroutines.flow.Flow

interface GeofenceRepository {
    val geofenceFlow: Flow<List<LockGeofence>>
    suspend fun addGeofence(
        name: String,
        deviceId: String,
        lat: Double,
        lng: Double,
        radius: Float,
        transition: GeofenceTransition,
    )
    suspend fun updateGeofence(geofence: LockGeofence)
    suspend fun removeGeofence(id: String)
}
