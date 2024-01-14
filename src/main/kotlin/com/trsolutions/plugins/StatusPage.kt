package com.trsolutions.plugins

import com.trsolutions.configurations.commons.app.http.Response
import io.klogging.logger
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*


fun Application.configureStatusPage() {

   install(StatusPages) {

      exception<Throwable> { call, cause ->

         logger("com.trsolutions.plugins.Application.configureRouting").error { ">>>> ${cause.message}" }

         when (cause) {
            is NotFoundException -> {
               call.respond(
                  HttpStatusCode.NotFound,
                  Response.jsonBody(listOf())
               )
            }

            else -> {
               call.respond(
                  HttpStatusCode.InternalServerError,
                  Response.jsonBody(listOf("OPS! Algo deu errado. Se o problema persistir, contacte o suporte."))
               )
            }
         }

      }
   }
}