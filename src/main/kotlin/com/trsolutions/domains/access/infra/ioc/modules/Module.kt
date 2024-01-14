package com.trsolutions.domains.access.infra.ioc.modules

import com.trsolutions.configurations.security.services.KeyService
import com.trsolutions.domains.access.app.services.SignInService
import com.trsolutions.domains.access.app.services.SignUpService
import com.trsolutions.domains.access.app.services.interfaces.ISignInService
import com.trsolutions.domains.access.app.services.interfaces.ISignUpService
import com.trsolutions.domains.access.core.interfaces.ISignInRepository
import com.trsolutions.domains.access.core.interfaces.ISignUpRepository
import com.trsolutions.domains.access.infra.data.repositories.SignInRepository
import com.trsolutions.domains.access.infra.data.repositories.SignUpRepository
import com.trsolutions.domains.access.infra.data.sql.CompanySQL
import com.trsolutions.domains.access.infra.data.sql.UserSQL
import com.trsolutions.domains.access.infra.data.tables.CompanyTable
import com.trsolutions.domains.access.infra.data.tables.RoleTable
import com.trsolutions.domains.access.infra.data.tables.UserTable
import com.trsolutions.domains.access.infra.data.tables.interfaces.ICompanyTable
import com.trsolutions.domains.access.infra.data.tables.interfaces.IRoleTable
import com.trsolutions.domains.access.infra.data.tables.interfaces.IUserTable
import org.koin.dsl.bind
import org.koin.dsl.module


object Module {

   val accessModule = module {

      single { KeyService() }

      single { UserSQL() }
      single { CompanySQL() }

      single { CompanyTable() } bind ICompanyTable::class
      single { UserTable() } bind IUserTable::class
      single { RoleTable() } bind IRoleTable::class

      factory { SignInRepository() } bind ISignInRepository::class
      factory { SignUpRepository() } bind ISignUpRepository::class
      factory { SignInService() } bind ISignInService::class
      factory { SignUpService() } bind ISignUpService::class
   }
}
