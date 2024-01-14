package com.trsolutions.domains.access.app.services

import com.trsolutions.domains.access.app.models.SignUpModel
import com.trsolutions.domains.access.app.services.interfaces.ISignUpService
import com.trsolutions.domains.access.core.interfaces.ISignUpRepository
import io.klogging.logger
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class SignUpService : ISignUpService, KoinComponent {

   private val repository: ISignUpRepository by inject()

   override suspend fun createAccount(model: SignUpModel, schema: String): Result<Unit> = runCatching {

      val user = model.convertToEntity()
      repository.insert(user, schema).getOrThrow()

   }.onFailure {
      logger<SignUpService>().error { "Erro no signUpService ${it.message}" }
   }
}
