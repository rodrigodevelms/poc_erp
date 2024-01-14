package com.trsolutions.configurations.database

import com.trsolutions.configurations.hocon.HoconConfiguration.readHoconProperty
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database


object PostgresConfiguration {

   private const val POOL_SIZE: Int = 3

   fun connect() {

      fun hikari(): HikariDataSource {

         val config = HikariConfig()

         config.jdbcUrl = readHoconProperty("postgres.url")
         config.driverClassName = readHoconProperty("postgres.driver")
         config.username = readHoconProperty("postgres.user")
         config.password = readHoconProperty("postgres.password")
         config.maximumPoolSize = POOL_SIZE
         config.isAutoCommit = false
         config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
         config.validate()

         return HikariDataSource(config)
      }

      Database.connect(hikari())
   }

}
