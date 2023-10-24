package com.seo4d696b75.android.switchbot_lock_ext.api.response

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class SwitchBotApiResponse(
    val statusCode: Int,
    val message: String,
    val body: JsonElement,
)
