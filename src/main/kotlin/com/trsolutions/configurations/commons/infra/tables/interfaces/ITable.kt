package com.trsolutions.configurations.commons.infra.tables.interfaces

import org.jetbrains.exposed.sql.ResultRow


interface ITable<T, R> {

   fun toTable(schema: String): T

   fun toEntity(schema: String, row: ResultRow): R
}
