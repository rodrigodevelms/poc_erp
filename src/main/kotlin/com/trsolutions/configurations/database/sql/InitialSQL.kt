package com.trsolutions.configurations.database.sql

import com.trsolutions.domains.access.infra.data.tables.CompanyTable
import com.trsolutions.domains.access.infra.data.tables.RoleTable
import com.trsolutions.domains.access.infra.data.tables.UserTable
import org.jetbrains.exposed.sql.Schema
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction


class InitialSQL {

   suspend fun create(schema: String) = newSuspendedTransaction {

      createTRSolutionsSchema(schema)
      createAccessUser(schema)
      createAccessCompany(schema)
      createAccessRole(schema)
   }

   private fun createTRSolutionsSchema(schema: String) = SchemaUtils.createSchema(Schema(schema))

   private fun createAccessUser(schema: String) = SchemaUtils.create(UserTable().toTable(schema))
   private fun createAccessCompany(schema: String) = SchemaUtils.create(CompanyTable().toTable(schema))
   private fun createAccessRole(schema: String) = SchemaUtils.create(RoleTable().toTable(schema))
}
