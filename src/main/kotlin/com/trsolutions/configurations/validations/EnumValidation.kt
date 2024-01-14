package com.trsolutions.configurations.validations

import kotlin.reflect.KClass


fun <T : Enum<T>> validateEnum(enum: KClass<T>, value: String): String? {
   val result = enum.java.enumConstants.any { it.name == value }
   return when (result) {
      true -> null
      false -> "O valor $value não corresponde a nenhum valor permitido."
   }
}