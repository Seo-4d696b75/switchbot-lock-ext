package com.seo4d696b75.android.switchbot_lock_ext.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.seo4d696b75.android.switchbot_lock_ext.domain.geo.GeofenceTransition
import com.seo4d696b75.android.switchbot_lock_ext.domain.geo.LockGeofence

@Entity(tableName = "lock_geofence_tb")
data class LockGeofenceEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "enabled")
    val enabled: Boolean,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "device_id")
    val deviceId: String,
    @ColumnInfo(name = "lat")
    val lat: Double,
    @ColumnInfo(name = "lng")
    val lng: Double,
    @ColumnInfo(name = "radius")
    val radius: Float,
    @ColumnInfo(name = "transition")
    val transition: GeofenceTransition,
) {
    fun toModel() = LockGeofence(
        id = id,
        enabled = enabled,
        name = name,
        deviceId = deviceId,
        lat = lat,
        lng = lng,
        radius = radius,
        transition = transition,
    )

    companion object {
        fun fromModel(model: LockGeofence) = LockGeofenceEntity(
            id = model.id,
            enabled = model.enabled,
            name = model.name,
            deviceId = model.deviceId,
            lat = model.lat,
            lng = model.lng,
            radius = model.radius,
            transition = model.transition,
        )
    }
}
