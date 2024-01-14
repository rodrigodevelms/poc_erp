package com.trsolutions.domains.access.app.services.interfaces

import com.trsolutions.configurations.commons.app.services.interfaces.IService
import com.trsolutions.domains.access.app.models.SignInModel
import com.trsolutions.domains.access.app.models.SignUpModel


interface ISignUpService : IService<SignInModel> {

   suspend fun createAccount(model: SignUpModel, schema: String): Result<Unit>
}
