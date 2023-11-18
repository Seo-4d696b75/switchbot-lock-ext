package com.seo4d696b75.android.switchbot_lock_ext.domain.widget

import kotlinx.serialization.Serializable

@Serializable
data class LockWidgetState(
    val deviceId: String,
    val deviceName: String,
    val status: LockWidgetStatus,
)
