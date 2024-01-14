package com.trsolutions.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.trsolutions.configurations.hocon.HoconConfiguration.readHoconProperty
import com.trsolutions.configurations.security.services.HeaderService
import com.trsolutions.configurations.security.services.KeyService
import io.ktor.http.*
import io.ktor.http.auth.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*


fun Application.configureSecurity() {

   val service = KeyService()
   val jwtAudience = readHoconProperty("security.jwt.audience")
   val jwtRealm = readHoconProperty("security.jwt.realm")
   val jwtIssuer = readHoconProperty("security.jwt.issuer")
   val publicKey = service.readPublicKey(readHoconProperty("security.keys.public.path"))
   val privateKey = service.readPrivateKey(
      readHoconProperty("security.keys.private.path"),
      readHoconProperty("security.keys.private.secret")
   )

   authentication {

      jwt {

         authHeader {

            parseAuthorizationHeader(HeaderService().decryptToken(it))
         }

         realm = jwtRealm
         verifier(
            JWT
               .require(Algorithm.RSA256(publicKey, privateKey))
               .withAudience(jwtAudience)
               .withIssuer(jwtIssuer)
               .build()
         )

         validate { credential ->
            if (credential.payload.audience.contains(jwtAudience)) JWTPrincipal(credential.payload) else null
         }
         challenge { _, _ ->
            call.respond(HttpStatusCode.Unauthorized, "Token is not valid or has expired")
         }
      }
   }
}
