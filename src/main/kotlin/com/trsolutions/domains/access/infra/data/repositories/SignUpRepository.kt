package com.trsolutions.domains.access.infra.data.repositories

import com.trsolutions.domains.access.core.entities.User
import com.trsolutions.domains.access.core.interfaces.ISignUpRepository
import com.trsolutions.domains.access.infra.data.sql.UserSQL
import io.klogging.logger
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction


class SignUpRepository : ISignUpRepository {

   private val sql = UserSQL()

   override suspend fun insert(entity: User, schema: String): Result<Unit> = newSuspendedTransaction {

      runCatching { sql.insert(entity, schema) }
         .onFailure {
            rollback()
            logger<SignUpRepository>().error("Erro: SignUpRepository.insert :: Mensagem: ${it.message}")
            throw it
         }
   }
}
