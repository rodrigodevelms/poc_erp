package com.trsolutions.domains.access.app.services.interfaces

import com.trsolutions.configurations.commons.app.services.interfaces.IService
import com.trsolutions.domains.access.app.models.SignInModel


interface ISignInService : IService<SignInModel> {

   suspend fun login(model: SignInModel, schema: String): Result<String?>
}