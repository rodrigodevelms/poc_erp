package com.trsolutions.domains.access.infra.data.repositories

import com.trsolutions.domains.access.core.entities.User
import com.trsolutions.domains.access.core.interfaces.ISignInRepository
import com.trsolutions.domains.access.infra.data.sql.UserSQL
import io.klogging.logger
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction


class SignInRepository : ISignInRepository {

   private val sql = UserSQL()
   override suspend fun select(email: String, schema: String): Result<User?> = newSuspendedTransaction {

      runCatching { sql.selectByEmail(email, schema) }
         .onFailure {
            logger<SignInRepository>().error { "Erro: SignInRepository.select :: Menssagem: ${it.message}" }
            throw it
         }
   }
}