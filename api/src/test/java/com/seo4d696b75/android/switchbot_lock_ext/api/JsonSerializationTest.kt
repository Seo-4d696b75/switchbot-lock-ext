package com.seo4d696b75.android.switchbot_lock_ext.api

import com.seo4d696b75.android.switchbot_lock_ext.api.response.device.PhysicalDevice
import com.seo4d696b75.android.switchbot_lock_ext.api.response.status.PhysicalDeviceStatus
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Test

class JsonSerializationTest {

    private val json = Json {
        ignoreUnknownKeys = true
    }

    @Test
    fun deserialize_LockDevice() {
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
        val device: PhysicalDevice = json.decodeFromString(str)
        assertEquals("500291B269BE", device.id)
        assertEquals("Door Lock", device.name)
    }

    @Test
    fun deserialize_list_PhysicalDevice() {

        val str = """[
        {
            "deviceId": "500291B269BE",
            "deviceName": "Living Room Humidifier",
            "deviceType": "Humidifier",
            "enableCloudService": true,
            "hubDeviceId": "000000000000"
        },
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
        ]"""
        val devices: List<PhysicalDevice> = json.decodeFromString(str)
        assertEquals("500291B269BE", devices[1].id)
    }

    @Test
    fun deserialize_LockDeviceStatus() {
        val str = """{
            "deviceId": "500291B269BE",
            "deviceName": "Living Door",
            "deviceType": "Smart Lock",
            "enableCloudService": true,
            "hubDeviceId": "000000000000",
            "battery": 90,
            "version": "V4.2",
            "lockState": "locked",
            "doorState": "closed",
            "calibrate": true
        }"""
        val status: PhysicalDeviceStatus = json.decodeFromString(str)
        assertEquals("locked", status.lockState)
    }
}
