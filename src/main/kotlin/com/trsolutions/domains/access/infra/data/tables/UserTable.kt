package com.trsolutions.domains.access.infra.data.tables

import com.trsolutions.domains.access.core.entities.User
import com.trsolutions.domains.access.infra.data.tables.interfaces.IUserTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime


class UserTable : IUserTable {

   abstract class UserTable(schema: String, name: String = ".access_user") : Table("$schema$name") {

      val id = uuid("id")
      val internalId = long("internal_id").autoIncrement()
      val active = bool("active")
      val name = varchar("name", 150)
      val email = varchar("email", 150).uniqueIndex("access_user_email_vcindex")
      val password = varchar("password", 255)
      val createdBy = uuid("created_by")
      val createdAt = datetime("created_at")
      val modifiedBy = uuid("modified_by")
      val modifiedAt = datetime("modified_at")

      override val primaryKey: PrimaryKey = PrimaryKey(id, name = "access_user_id_uindex")
   }

   override fun toTable(schema: String): UserTable = object : UserTable(schema) {}

   override fun toEntity(schema: String, row: ResultRow): User {

      val table = toTable(schema)

      return User(
         id = row[table.id],
         active = row[table.active],
         name = row[table.name],
         email = row[table.email],
         password = row[table.password],
      )

   }
}
