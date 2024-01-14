package com.trsolutions.domains.access.infra.data.sql

import com.trsolutions.domains.access.core.entities.Company
import com.trsolutions.domains.access.infra.data.tables.interfaces.ICompanyTable
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.selectAll
import org.koin.java.KoinJavaComponent.getKoin
import java.time.LocalDateTime
import java.util.*


class CompanySQL {

   private val db: ICompanyTable = getKoin().get()

   fun selectAllByFk(schema: String, id: UUID): List<Company> {

      val table = db.toTable(schema)

      val result = table
         .selectAll()
         .andWhere { table.userFk eq id }
         .map { db.toEntity(schema, it) }

      return result.takeIf { it.isNotEmpty() } ?: throw NullPointerException()
   }

   fun batchInsert(companies: Collection<Company>, schema: String) {

      val table = db.toTable(schema)

      table
         .batchInsert(companies) { company ->
            this[table.id] = company.id
            this[table.active] = company.active
            this[table.name] = company.name
            this[table.hash] = company.hash
            this[table.documentFilePath] = company.documentFilePath
            this[table.userFk] = company.userFk
            this[table.createdBy] = company.id
            this[table.createdAt] = LocalDateTime.now()
            this[table.modifiedBy] = company.id
            this[table.modifiedAt] = LocalDateTime.now()
         }
   }
}
