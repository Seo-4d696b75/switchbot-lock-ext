package com.seo4d696b75.android.switchbot_lock_ext.data.geo

import com.google.android.gms.location.Geofence
import com.seo4d696b75.android.switchbot_lock_ext.domain.geo.GeofenceTransition
import com.seo4d696b75.android.switchbot_lock_ext.domain.geo.LockGeofence

val LockGeofence.requestId: String
    get() = "switchbot-lock-ext-$id"

fun LockGeofence.toGeofence() = Geofence.Builder()
    .setRequestId(requestId)
    .setCircularRegion(lat, lng, radius)
    .setExpirationDuration(Geofence.NEVER_EXPIRE)
    .setTransitionTypes(transition.toType())
    .build()

fun GeofenceTransition.toType() = when (this) {
    GeofenceTransition.Enter -> Geofence.GEOFENCE_TRANSITION_ENTER
    GeofenceTransition.Exit -> Geofence.GEOFENCE_TRANSITION_EXIT
}

fun Int.toGeofenceTransition() = when (this) {
    Geofence.GEOFENCE_TRANSITION_ENTER -> GeofenceTransition.Enter
    Geofence.GEOFENCE_TRANSITION_EXIT -> GeofenceTransition.Exit
    else -> throw IllegalArgumentException()
}
