package com.trsolutions.domains.access.core.interfaces

import com.trsolutions.configurations.commons.infra.repositories.interfaces.IRepository
import com.trsolutions.domains.access.core.entities.User


interface ISignInRepository : IRepository<User> {

   suspend fun select(email: String, schema: String): Result<User?>
}