package com.trsolutions.domains.access.app.controllers

import com.trsolutions.configurations.commons.app.http.Response.Companion.jsonBody
import com.trsolutions.domains.access.app.models.SignInModel
import com.trsolutions.domains.access.app.models.SignUpModel
import com.trsolutions.domains.access.app.services.interfaces.ISignInService
import com.trsolutions.domains.access.app.services.interfaces.ISignUpService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.getKoin


fun Route.access() {

   route("/access") {

      post("/signUp") {

         val service: ISignUpService = getKoin().get()
         val signUpModel = call.receive<SignUpModel>()
         val validation = signUpModel.validate()

         when (validation.isEmpty()) {
            true -> {
               service.createAccount(signUpModel, "trsolutions").getOrThrow()
               call.respond(HttpStatusCode.Created, jsonBody(listOf("OK")))
            }

            false -> {
               call.respond(HttpStatusCode.BadRequest, jsonBody(validation))
            }
         }
      }

      post("/signIn") {

         val service: ISignInService = getKoin().get()
         val signInModel = call.receive<SignInModel>()
         val validation = signInModel.validate()

         when (validation.isEmpty()) {

            true -> {
               val result = service.login(signInModel, "trsolutions").getOrThrow()

               when (result.isNullOrBlank()) {
                  true -> call.respond(HttpStatusCode.Forbidden)
                  false -> call.respond(HttpStatusCode.OK, jsonBody(listOfNotNull(result)))
               }
            }

            false -> {
               call.respond(HttpStatusCode.BadRequest, jsonBody(validation))
            }
         }
      }
   }

}
