package com.trsolutions.domains.access.infra.data.tables

import com.trsolutions.domains.access.core.entities.Role
import com.trsolutions.domains.access.core.enums.RoleType
import com.trsolutions.domains.access.infra.data.tables.interfaces.IRoleTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table

class RoleTable : IRoleTable {

   abstract class RoleTable(schema: String, name: String = ".access_role") : Table("$schema$name") {

      val id = uuid("id")
      val internalId = long("internal_id").autoIncrement()
      val type = varchar("type", 20)

      val userFk = reference("user_fk", UserTable().toTable(schema).id, ReferenceOption.CASCADE)

      override val primaryKey: PrimaryKey = PrimaryKey(id, name = "access_role_id_uindex")
   }

   override fun toTable(schema: String): RoleTable = object : RoleTable(schema) {}

   override fun toEntity(schema: String, row: ResultRow): Role {

      val table = toTable(schema)

      return Role(
         id = row[table.id],
         type = RoleType.valueOf(row[table.type]),
         userFK = row[table.userFk]
      )
   }
}