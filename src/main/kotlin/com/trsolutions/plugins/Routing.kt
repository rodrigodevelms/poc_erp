package com.trsolutions.plugins

import com.trsolutions.domains.access.app.controllers.access
import io.ktor.server.application.*
import io.ktor.server.routing.*


fun Application.configureRouting() {

   routing {

      route("api") {
         access()
      }
   }
}

