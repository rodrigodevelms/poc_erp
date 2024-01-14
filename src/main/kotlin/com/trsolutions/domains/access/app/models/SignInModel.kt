package com.trsolutions.domains.access.app.models

import com.trsolutions.configurations.commons.app.models.interfaces.IModel
import com.trsolutions.configurations.validations.validateEmail
import com.trsolutions.configurations.validations.validateString
import kotlinx.serialization.Serializable

@Serializable
data class SignInModel(
   val email: String,
   val password: String
) : IModel {

   override fun validate(): List<String> = listOfNotNull(

      validateEmail(email),
      validateString("SENHA", password, 15, 30)
   )
}
