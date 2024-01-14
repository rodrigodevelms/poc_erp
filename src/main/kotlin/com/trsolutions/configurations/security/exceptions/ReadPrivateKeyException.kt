package com.trsolutions.configurations.security.exceptions


class ReadPrivateKeyException(message: String) : Exception(message) {

   companion object {

      const val READ_PRIVATE_KEY_EXCEPTION_MESSAGE = "Was not possible to read the private key."
   }
}
