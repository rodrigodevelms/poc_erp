package com.trsolutions.configurations.validations

import java.util.regex.Pattern


fun validateDDD(ddd: String): String? {

   val dddPattern = "^([1-9]?[1-9]?[1-9][1-9])$"

   return when {
      ddd.trim().isBlank() -> "O campo DDD não pode ser vazio. "
      !Pattern.compile(dddPattern).matcher(ddd).matches() -> "O campo DDD deve conter um DDD válido."
      else -> null
   }
}

fun validatePhone(phone: String): String? {

   val phonePattern = "^(?:[2-8]|9[1-9])[0-9]{3} [0-9]{4}$"

   return when {
      phone.trim().isBlank() -> "O campo NÚMERO DE TELEFONE não pode ser vazio."
      !Pattern.compile(phonePattern).matcher(phone).matches() -> "O campo NÚMERO DE TELEFONE deve conter um número de telefone válido."
      else -> null
   }
}
