package com.trsolutions.domains.access.core.interfaces

import com.trsolutions.configurations.commons.infra.repositories.interfaces.IRepository
import com.trsolutions.domains.access.core.entities.User


interface ISignUpRepository : IRepository<User> {

   suspend fun insert(entity: User, schema: String): Result<Unit>
}
