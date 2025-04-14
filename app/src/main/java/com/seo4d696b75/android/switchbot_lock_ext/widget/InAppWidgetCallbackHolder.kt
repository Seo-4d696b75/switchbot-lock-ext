package com.seo4d696b75.android.switchbot_lock_ext.widget

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InAppWidgetCallbackHolder @Inject constructor() {

    private var deviceId: String? = null

    fun enqueue(deviceId: String) {
        this.deviceId = deviceId
    }

    fun pop(): String? = deviceId?.also {
        this.deviceId = null
    }
}
