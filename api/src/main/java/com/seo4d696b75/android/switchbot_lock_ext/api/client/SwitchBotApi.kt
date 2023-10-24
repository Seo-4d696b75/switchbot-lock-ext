package com.seo4d696b75.android.switchbot_lock_ext.api.client

import com.seo4d696b75.android.switchbot_lock_ext.api.response.DeviceCollection
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.LockDeviceStatus
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface SwitchBotApi {

    @GET("/v1.1/devices")
    suspend fun getDevicesRaw(): Response<ResponseBody>

    @GET("/v1.1/devices")
    suspend fun getDevices(): DeviceCollection

    @GET("/v1.1/devices/{id}/status")
    suspend fun getStatus(@Path("id") deviceId: String): LockDeviceStatus
}
