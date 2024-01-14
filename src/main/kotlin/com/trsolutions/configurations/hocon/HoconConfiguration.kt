package com.trsolutions.configurations.hocon

import com.typesafe.config.ConfigFactory
import io.ktor.server.config.*


object HoconConfiguration {

   private val environment = System.getenv("KTOR_ENV")
   private val hoconApplicationConfig = HoconApplicationConfig(ConfigFactory.load())

   fun readHoconProperty(key: String): String = hoconApplicationConfig.property("$environment.$key").getString()
}
