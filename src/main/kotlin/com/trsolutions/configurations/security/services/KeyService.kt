package com.trsolutions.configurations.security.services

import com.trsolutions.configurations.hocon.HoconConfiguration.readHoconProperty
import com.trsolutions.configurations.security.exceptions.ReadPrivateKeyException
import com.trsolutions.configurations.security.exceptions.ReadPrivateKeyException.Companion.READ_PRIVATE_KEY_EXCEPTION_MESSAGE
import com.trsolutions.configurations.security.provider.BouncyCastleProvider.addProvider
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo
import org.bouncycastle.openssl.PEMEncryptedKeyPair
import org.bouncycastle.openssl.PEMParser
import org.bouncycastle.openssl.bc.BcPEMDecryptorProvider
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter
import org.bouncycastle.operator.InputDecryptorProvider
import org.bouncycastle.pkcs.PKCS8EncryptedPrivateKeyInfo
import org.bouncycastle.pkcs.jcajce.JcePKCSPBEInputDecryptorProviderBuilder
import java.io.FileInputStream
import java.io.FileReader
import java.security.KeyStore
import java.security.PrivateKey
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey


class KeyService {

   fun retrievePrivateKey(): PrivateKey {

      addProvider

      val keyStore = KeyStore.getInstance("PKCS12")

      keyStore.load(
         FileInputStream(readHoconProperty("security.crypto.key.p12")),
         readHoconProperty("security.crypto.password").toCharArray()
      )

      return keyStore.getKey(
         readHoconProperty("security.crypto.alias"),
         readHoconProperty("security.crypto.password").toCharArray()
      ) as PrivateKey

   }

   fun readPublicKey(filePath: String): RSAPublicKey {

      val bytes = PEMParser(FileReader(filePath)).readObject()
      val keyInfo = SubjectPublicKeyInfo.getInstance(bytes)

      return JcaPEMKeyConverter().getPublicKey(keyInfo) as RSAPublicKey
   }

   fun readPrivateKey(filePath: String, password: String): RSAPrivateKey {

      addProvider

      val privateKey = when (val keyObject: Any = PEMParser(FileReader(filePath)).readObject()) {

         is PKCS8EncryptedPrivateKeyInfo -> {
            val decryptorProvider: InputDecryptorProvider =
               JcePKCSPBEInputDecryptorProviderBuilder()
                  .setProvider("BC")
                  .build(password.toCharArray())

            keyObject.decryptPrivateKeyInfo(decryptorProvider)
         }

         is PEMEncryptedKeyPair -> {
            val decryptorProvider = BcPEMDecryptorProvider(password.toCharArray())
            val pemKeyPair = keyObject.decryptKeyPair(decryptorProvider)

            pemKeyPair.privateKeyInfo
         }

         else -> throw ReadPrivateKeyException(READ_PRIVATE_KEY_EXCEPTION_MESSAGE)
      }

      return JcaPEMKeyConverter()
         .setProvider("BC")
         .getPrivateKey(privateKey) as RSAPrivateKey
   }
}
