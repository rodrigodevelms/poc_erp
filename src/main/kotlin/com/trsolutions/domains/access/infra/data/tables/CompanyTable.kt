package com.trsolutions.domains.access.infra.data.tables

import com.trsolutions.domains.access.core.entities.Company
import com.trsolutions.domains.access.infra.data.tables.interfaces.ICompanyTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime


class CompanyTable : ICompanyTable {
   abstract class CompanyTable(schema: String, name: String = ".access_company") : Table("$schema$name") {

      val id = uuid("id")
      val internalId = long("internal_id").autoIncrement()
      val active = bool("active")
      val name = varchar("name", 150)
      val hash = uuid("hash").uniqueIndex("access_company_hash_uindex")
      val documentFilePath = text("document_file_path")
      val createdBy = uuid("created_by")
      val createdAt = datetime("created_at")
      val modifiedBy = uuid("modified_by")
      val modifiedAt = datetime("modified_at")

      val userFk = reference("user_fk", UserTable().toTable(schema).id, ReferenceOption.CASCADE)

      override val primaryKey = PrimaryKey(id, name = "access_company_id_uindex")
   }

   override fun toTable(schema: String): CompanyTable = object : CompanyTable(schema) {}

   override fun toEntity(schema: String, row: ResultRow): Company {

      val table = toTable(schema)

      return Company(
         id = row[table.id],
         active = row[table.active],
         name = row[table.name],
         hash = row[table.hash],
         documentFilePath = row[table.documentFilePath],
         userFk = row[table.userFk]
      )
   }
}
