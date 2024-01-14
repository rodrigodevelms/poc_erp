package com.trsolutions.domains.access.core.entities

import com.trsolutions.configurations.commons.core.entities.interfaces.IEntity
import java.util.*


data class User(
   override val id: UUID,
   val active: Boolean,
   val name: String,
   val email: String,
   val password: String,

   val companies: Collection<Company>? = null,
   val roles: Collection<Role>? = null
) : IEntity
