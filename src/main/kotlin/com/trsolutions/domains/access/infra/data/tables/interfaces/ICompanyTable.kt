package com.trsolutions.domains.access.infra.data.tables.interfaces

import com.trsolutions.configurations.commons.infra.tables.interfaces.ITable
import com.trsolutions.domains.access.core.entities.Company
import com.trsolutions.domains.access.infra.data.tables.CompanyTable


interface ICompanyTable : ITable<CompanyTable.CompanyTable, Company>
