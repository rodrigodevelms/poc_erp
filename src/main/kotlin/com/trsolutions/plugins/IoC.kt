package com.trsolutions.plugins

import com.trsolutions.domains.access.infra.ioc.modules.Module.accessModule
import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger


fun Application.configureIoC() {

   install(Koin) {

      slf4jLogger()

      modules(accessModule)
   }
}
