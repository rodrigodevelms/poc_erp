package com.trsolutions.configurations.security.services

import com.trsolutions.configurations.security.extensions.decrypt
import io.ktor.server.application.*


class HeaderService {

   fun decryptToken(call: ApplicationCall): String {

      val jwtEncrypted: String = call
         .request
         .headers["Authorization"]
         ?: throw Exception()


      val jwtDecrypted: String = jwtEncrypted
         .replace("Bearer", "")
         .trim()
         .decrypt()

      return jwtDecrypted.let { "Bearer $it" }
   }
}
