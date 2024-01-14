package com.trsolutions.configurations.validations


fun validateString(fieldName: String, value: String, minimumValue: Int, maximumValue: Int): String? {

   return when {
      value.isBlank() -> "O campo $fieldName não pode ser vazio."
      value.length < minimumValue || value.length > maximumValue -> "O campo $fieldName deve conter entre $minimumValue e $maximumValue caracters."
      else -> null
   }
}
