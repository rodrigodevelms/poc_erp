package com.trsolutions.domains.access.core.entities

import com.trsolutions.configurations.commons.core.entities.interfaces.IEntity
import java.util.*


data class Company(
   override val id: UUID,
   val active: Boolean,
   val name: String,
   val hash: UUID,
   val documentFilePath: String,
   val userFk: UUID
) : IEntity
