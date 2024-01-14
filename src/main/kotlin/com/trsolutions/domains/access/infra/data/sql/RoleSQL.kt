package com.trsolutions.domains.access.infra.data.sql

import com.trsolutions.domains.access.core.entities.Role
import com.trsolutions.domains.access.infra.data.tables.interfaces.IRoleTable
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.selectAll
import org.koin.java.KoinJavaComponent.getKoin
import java.util.*


class RoleSQL {

   private val db: IRoleTable = getKoin().get()

   fun selectAllByFk(schema: String, id: UUID): List<Role> {

      val table = db.toTable(schema)

      val result = table
         .selectAll()
         .andWhere { table.userFk eq id }
         .map { db.toEntity(schema, it) }

      return result.takeIf { it.isNotEmpty() } ?: throw NullPointerException()
   }

   fun batchInsert(schema: String, roles: Collection<Role>) {

      val table = db.toTable(schema)

      table
         .batchInsert(roles) { role ->
            this[table.id] = role.id
            this[table.type] = role.type.name
            this[table.userFk] = role.userFK
         }
   }
}
