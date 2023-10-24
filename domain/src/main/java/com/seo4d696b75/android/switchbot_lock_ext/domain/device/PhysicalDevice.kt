package com.seo4d696b75.android.switchbot_lock_ext.domain.device

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive

@Serializable(with = PhysicalDeviceSerializer::class)
sealed interface PhysicalDevice {
    val id: String
    val name: String
    val type: String
    val enableCloudService: Boolean
    val hubDeviceId: String

    @Serializable
    data class Base(
        @SerialName("deviceId")
        override val id: String,
        @SerialName("deviceName")
        override val name: String,
        @SerialName("deviceType")
        override val type: String,
        override val enableCloudService: Boolean,
        override val hubDeviceId: String,
    ) : PhysicalDevice

    @Serializable
    data class Lock(
        @SerialName("deviceId")
        override val id: String,
        @SerialName("deviceName")
        override val name: String,
        @SerialName("deviceType")
        override val type: String,
        override val enableCloudService: Boolean,
        override val hubDeviceId: String,
        val group: Boolean = false,
        val groupName: String? = null,
        val master: Boolean = true,
    ) : PhysicalDevice
}

class PhysicalDeviceSerializer : KSerializer<PhysicalDevice> {
    override val descriptor: SerialDescriptor
        get() = buildClassSerialDescriptor("PhysicalDevice") {
            element<String>("deviceType")
        }

    override fun deserialize(decoder: Decoder): PhysicalDevice {
        require(decoder is JsonDecoder)
        val element = decoder.decodeJsonElement()
        require(element is JsonObject)
        val type = requireNotNull(element["deviceType"]).jsonPrimitive.content
        return if (type == "Smart Lock") {
            decoder.json.decodeFromJsonElement(
                PhysicalDevice.Lock.serializer(),
                element,
            )
        } else {
            decoder.json.decodeFromJsonElement(
                PhysicalDevice.Base.serializer(),
                element,
            )
        }
    }

    override fun serialize(encoder: Encoder, value: PhysicalDevice) {
        when (value) {
            is PhysicalDevice.Base -> encoder.encodeSerializableValue(
                PhysicalDevice.Base.serializer(),
                value,
            )

            is PhysicalDevice.Lock -> encoder.encodeSerializableValue(
                PhysicalDevice.Lock.serializer(),
                value,
            )
        }
    }

}

