package com.trsolutions.domains.access.infra.data.tables.interfaces

import com.trsolutions.configurations.commons.infra.tables.interfaces.ITable
import com.trsolutions.domains.access.core.entities.Role
import com.trsolutions.domains.access.infra.data.tables.RoleTable

interface IRoleTable : ITable<RoleTable.RoleTable, Role>