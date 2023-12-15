package com.seo4d696b75.android.switchbot_lock_ext.domain.geo

data class LockGeofence(
    val id: String,
    val enabled: Boolean,
    val lat: Double,
    val lng: Double,
    val radius: Float,
    val transition: GeofenceTransition,
)

enum class GeofenceTransition {
    Enter,
    Exit,
}
