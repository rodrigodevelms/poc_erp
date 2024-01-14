package com.trsolutions.domains.access.core.entities

import com.trsolutions.configurations.commons.core.entities.interfaces.IEntity
import com.trsolutions.domains.access.core.enums.RoleType
import java.util.*


data class Role(
   override val id: UUID,
   val type: RoleType,
   val userFK: UUID
) : IEntity
