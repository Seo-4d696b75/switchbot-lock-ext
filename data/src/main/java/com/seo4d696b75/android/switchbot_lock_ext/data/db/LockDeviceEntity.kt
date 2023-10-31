package com.seo4d696b75.android.switchbot_lock_ext.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.LockDevice
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.LockGroup

@Entity(tableName = "lock_device_tb")
data class LockDeviceEntity(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "cloud_service")
    val enableCloudService: Boolean,
    @ColumnInfo(name = "hub_device_id")
    val hubDeviceId: String,
    @ColumnInfo(name = "group_name")
    val groupName: String?,
    @ColumnInfo(name = "master")
    val isMaster: Boolean,
) {
    fun toModel(): LockDevice {
        return LockDevice(
            id = id,
            name = name,
            enableCloudService = enableCloudService,
            hubDeviceId = hubDeviceId,
            group = if (groupName == null) {
                LockGroup.Disabled
            } else {
                LockGroup.Enabled(
                    groupName = groupName,
                    isMaster = isMaster,
                )
            }
        )
    }

    companion object {
        fun fromModel(model: LockDevice): LockDeviceEntity {
            val group = model.group
            return LockDeviceEntity(
                id = model.id,
                name = model.name,
                enableCloudService = model.enableCloudService,
                hubDeviceId = model.hubDeviceId,
                groupName = when (group) {
                    is LockGroup.Enabled -> group.groupName
                    LockGroup.Disabled -> null
                },
                isMaster = when (group) {
                    is LockGroup.Enabled -> group.isMaster
                    LockGroup.Disabled -> true
                },
            )
        }
    }
}
