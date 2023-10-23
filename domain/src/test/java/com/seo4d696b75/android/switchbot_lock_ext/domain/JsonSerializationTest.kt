package com.seo4d696b75.android.switchbot_lock_ext.domain

import com.seo4d696b75.android.switchbot_lock_ext.domain.device.LockDevice
import kotlinx.serialization.json.Json
import org.junit.Test

import org.junit.Assert.*

class JsonSerializationTest {

    private val json = Json {
        ignoreUnknownKeys = true
    }
    @Test
    fun serialize_LockDevice() {
        val str = """
        {
            "deviceId": "500291B269BE",
            "deviceName": "Door Lock",
            "deviceType": "Smart Lock",
            "enableCloudService": true,
            "hubDeviceId": "000000000000",
            "group": true,
            "groupName": "Door",
            "master": true,
            "lockDevicesIds": [
            "000000000001",
            "000000000002"
            ]
        }
        """
        val device: LockDevice = json.decodeFromString(str)
        assertEquals("500291B269BE", device.id)
        assertEquals("Door Lock", device.name)
    }
}
