package com.trsolutions.configurations.validations

import java.util.regex.Pattern


fun validateEmail(email: String): String? {

   val emailPattern =
      "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$"

   return when {
      email.trim().isEmpty() -> "O campo EMAIL não pode ser vazio."
      !Pattern.compile(emailPattern).matcher(email).matches() -> "O campo EMAIL deve conter um email válido. "
      else -> null
   }
}
