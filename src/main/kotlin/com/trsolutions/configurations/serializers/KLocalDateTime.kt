package com.trsolutions.configurations.serializers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.LocalDateTime


object KLocalDateTime : KSerializer<LocalDateTime> {

   override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("KLocalDateTime", PrimitiveKind.STRING)
   override fun deserialize(decoder: Decoder): LocalDateTime = LocalDateTime.parse(decoder.decodeString())
   override fun serialize(encoder: Encoder, value: LocalDateTime) = encoder.encodeString(value.toString())
}