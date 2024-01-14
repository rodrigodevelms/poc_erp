package com.trsolutions.configurations.commons.app.http

import com.trsolutions.configurations.serializers.KLocalDateTime
import com.trsolutions.configurations.serializers.KUUID
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.util.*


@Serializable
data class Response(
   @Serializable(KUUID::class) val id: UUID,
   @Serializable(KLocalDateTime::class) val date: LocalDateTime,
   val messages: List<String?>
) {

   companion object {

      fun jsonBody(messages: List<String?>) = Response(
         UUID.randomUUID(),
         LocalDateTime.now(),
         messages
      )
   }
}