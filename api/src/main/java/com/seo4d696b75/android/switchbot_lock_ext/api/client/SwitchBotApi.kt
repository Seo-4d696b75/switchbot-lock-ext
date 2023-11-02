package com.seo4d696b75.android.switchbot_lock_ext.api.client

import com.seo4d696b75.android.switchbot_lock_ext.api.request.command.DeviceCommandRequest
import com.seo4d696b75.android.switchbot_lock_ext.api.response.device.DeviceCollection
import com.seo4d696b75.android.switchbot_lock_ext.api.response.status.PhysicalDeviceStatus
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface SwitchBotApi {

    @GET("/v1.1/devices")
    suspend fun getDevices(): DeviceCollection

    @GET("/v1.1/devices/{id}/status")
    suspend fun getStatus(@Path("id") deviceId: String): PhysicalDeviceStatus

    @POST("/v1.1/devices/{id}/commands")
    suspend fun postCommand(
        @Path("id") deviceId: String,
        @Body command: DeviceCommandRequest,
    ): Unit
}
