package com.trsolutions.domains.access.app.services

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.trsolutions.configurations.hocon.HoconConfiguration.readHoconProperty
import com.trsolutions.configurations.security.extensions.checkEncoding
import com.trsolutions.configurations.security.extensions.encrypt
import com.trsolutions.configurations.security.services.KeyService
import com.trsolutions.domains.access.app.models.SignInModel
import com.trsolutions.domains.access.app.services.interfaces.ISignInService
import com.trsolutions.domains.access.core.entities.User
import com.trsolutions.domains.access.core.interfaces.ISignInRepository
import io.klogging.logger
import io.ktor.server.plugins.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.*


class SignInService : ISignInService, KoinComponent {

   private val key: KeyService by inject()
   private val repository: ISignInRepository by inject()

   override suspend fun login(model: SignInModel, schema: String): Result<String?> = runCatching {

      val user = repository.select(model.email, schema).getOrThrow()
         ?: return Result.failure<String>(NotFoundException());

      val checkEncoder = model.password.checkEncoding(user.password)

      if (!checkEncoder) null else generateJwt(user).encrypt()


   }.onFailure { logger<SignInService>().error { "Erro no SignInService ${it.message}" } }

   private fun generateJwt(user: User): String {

      val publicKey = key.readPublicKey(readHoconProperty("security.keys.public.path"))
      val privateKey = key.readPrivateKey(
         readHoconProperty("security.keys.private.path"),
         readHoconProperty("security.keys.private.secret")
      )

      return JWT.create()
         .withAudience(readHoconProperty("security.jwt.audience"))
         .withIssuer(readHoconProperty("security.jwt.issuer"))
         .withClaim("name", user.name)
         .withClaim("company", user.companies?.map { it.name })
         .withArrayClaim("roles", user.roles?.map { it.type.name }?.toTypedArray())
         .withExpiresAt(Date(System.currentTimeMillis() + 600000))
         .sign(Algorithm.RSA256(publicKey, privateKey))
   }
}
