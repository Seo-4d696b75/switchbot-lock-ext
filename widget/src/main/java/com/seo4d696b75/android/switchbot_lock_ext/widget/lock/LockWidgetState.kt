package com.seo4d696b75.android.switchbot_lock_ext.widget.lock

import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable

@Serializable
@OptIn(ExperimentalSerializationApi::class)
data class LockWidgetState(
    val deviceId: String,
    val deviceName: String,
    val status: LockWidgetStatus,
    @EncodeDefault(EncodeDefault.Mode.ALWAYS)
    val opacity: Float = 1f,
)
