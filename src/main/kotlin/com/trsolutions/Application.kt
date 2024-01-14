package com.trsolutions

import com.trsolutions.plugins.configureCors
import com.trsolutions.plugins.configureDatabases
import com.trsolutions.plugins.configureIoC
import com.trsolutions.plugins.configureRouting
import com.trsolutions.plugins.configureSecurity
import com.trsolutions.plugins.configureSerialization
import com.trsolutions.plugins.configureStatusPage
import com.trsolutions.plugins.configureSwagger
import io.ktor.server.application.*


fun main(args: Array<String>) {
   io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {

   configureSecurity()
   configureCors()
   configureIoC()
   configureSwagger()
   configureSerialization()
   configureDatabases()
   configureStatusPage()
   configureRouting()
}
