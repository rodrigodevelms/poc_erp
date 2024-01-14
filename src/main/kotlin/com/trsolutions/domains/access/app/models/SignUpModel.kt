package com.trsolutions.domains.access.app.models

import com.trsolutions.configurations.commons.app.models.interfaces.IModel
import com.trsolutions.configurations.security.extensions.encode
import com.trsolutions.configurations.validations.validateEmail
import com.trsolutions.configurations.validations.validateEnum
import com.trsolutions.configurations.validations.validateString
import com.trsolutions.domains.access.core.entities.Company
import com.trsolutions.domains.access.core.entities.Role
import com.trsolutions.domains.access.core.entities.User
import com.trsolutions.domains.access.core.enums.RoleType
import kotlinx.serialization.Serializable
import java.util.*


@Serializable
data class SignUpModel(
   val username: String,
   val password: String,
   val email: String,
   val company: String,
   val roles: Collection<String>
) : IModel {

   override fun validate(): List<String> = listOfNotNull(

      validateString("NOME DO USUÁRIO", username, 5, 150),
      validateString("SENHA", password, 15, 30),
      validateEmail(email),
      validateString("NOME FANTASIA", company, 5, 150),
   ).plus(roles.mapNotNull { validateEnum(RoleType::class, it) })

   fun convertToEntity(): User {

      val id = UUID.randomUUID()

      val newCompany = Company(
         id = UUID.randomUUID(),
         active = true,
         name = company,
         hash = UUID.randomUUID(),
         documentFilePath = "TODO",
         userFk = id
      )

      val newRoles = roles.map {
         Role(
            id = UUID.randomUUID(),
            type = RoleType.valueOf(it),
            userFK = id
         )
      }

      return User(
         id = id,
         active = true,
         name = username,
         email = email,
         password = password.encode(),
         companies = listOf(newCompany),
         roles = newRoles
      )
   }
}
