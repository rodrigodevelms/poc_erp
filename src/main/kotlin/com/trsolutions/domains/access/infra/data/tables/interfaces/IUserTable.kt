package com.trsolutions.domains.access.infra.data.tables.interfaces

import com.trsolutions.configurations.commons.infra.tables.interfaces.ITable
import com.trsolutions.domains.access.core.entities.User
import com.trsolutions.domains.access.infra.data.tables.UserTable


interface IUserTable : ITable<UserTable.UserTable, User>
