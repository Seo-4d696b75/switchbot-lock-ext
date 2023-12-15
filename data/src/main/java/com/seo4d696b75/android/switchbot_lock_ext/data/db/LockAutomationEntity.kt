package com.seo4d696b75.android.switchbot_lock_ext.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.seo4d696b75.android.switchbot_lock_ext.domain.automation.LockAutomation
import com.seo4d696b75.android.switchbot_lock_ext.domain.automation.LockAutomationType
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.LockDevice
import com.seo4d696b75.android.switchbot_lock_ext.domain.geo.GeofenceTransition
import com.seo4d696b75.android.switchbot_lock_ext.domain.geo.LockGeofence

@Entity(tableName = "lock_automation_tb")
data class LockAutomationEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "enabled")
    val enabled: Boolean,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "type")
    val type: LockAutomationType,
    @ColumnInfo(name = "device_id")
    val deviceId: String,
    @ColumnInfo(name = "geofence_id")
    val geofenceId: String,
) {
    fun toModel(device: LockDevice, geofence: LockGeofence): LockAutomation {
        require(device.id == deviceId)
        require(geofence.id == geofenceId)
        require(
            (geofence.transition == GeofenceTransition.Enter && type == LockAutomationType.Unlock)
                    || (geofence.transition == GeofenceTransition.Exit && type == LockAutomationType.Lock)
        )
        return LockAutomation(
            id = id,
            enabled = enabled,
            name = name,
            type = type,
            device = device,
            geofence = geofence,
        )
    }

    companion object {
        fun fromModel(model: LockAutomation) = LockAutomationEntity(
            id = model.id,
            enabled = model.enabled,
            name = model.name,
            type = model.type,
            deviceId = model.device.id,
            geofenceId = model.geofence.id,
        )
    }
}
