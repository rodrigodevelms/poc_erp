package com.trsolutions.domains.access.infra.data.sql

import com.trsolutions.domains.access.core.entities.User
import com.trsolutions.domains.access.infra.data.tables.interfaces.IUserTable
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.koin.java.KoinJavaComponent.getKoin
import java.time.LocalDateTime


class UserSQL {

   private val db: IUserTable = getKoin().get()

   fun selectByEmail(email: String, schema: String): User? {

      val table = db.toTable(schema)
      val companySQL = CompanySQL()
      val roleSQL = RoleSQL()

      val user = table
         .select { (table.email eq email) }
         .andWhere { table.active eq true }
         .mapNotNull { db.toEntity(schema, it) }

         .singleOrNull()

      val companies = user?.let { companySQL.selectAllByFk(schema, it.id) }
      val roles = user?.let { roleSQL.selectAllByFk(schema, it.id) }

      return user?.copy(companies = companies, roles = roles)
   }

   fun insert(user: User, schema: String) {

      val table = db.toTable(schema)
      val companySQL = CompanySQL()
      val roleSQL = RoleSQL()

      table
         .insert {
            it[id] = user.id
            it[active] = user.active
            it[name] = user.name
            it[email] = user.email
            it[password] = user.password
            it[createdBy] = user.id
            it[createdAt] = LocalDateTime.now()
            it[modifiedBy] = user.id
            it[modifiedAt] = LocalDateTime.now()
         }

      companySQL.batchInsert(
         companies = user.companies ?: throw NullPointerException(),
         schema = schema
      )
      roleSQL.batchInsert(
         roles = user.roles ?: throw NullPointerException(),
         schema = schema
      )

   }
}
