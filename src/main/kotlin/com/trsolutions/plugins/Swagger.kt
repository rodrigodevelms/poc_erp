package com.trsolutions.plugins

import io.ktor.server.application.*
import io.ktor.server.plugins.openapi.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.routing.*


fun Application.configureSwagger() {
   routing {
      openAPI(path = "openapi")
   }
   routing {
      swaggerUI(path = "openapi")
   }
}
