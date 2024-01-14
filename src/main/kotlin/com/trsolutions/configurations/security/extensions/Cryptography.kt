package com.trsolutions.configurations.security.extensions

import com.trsolutions.configurations.hocon.HoconConfiguration.readHoconProperty
import com.trsolutions.configurations.security.provider.BouncyCastleProvider.addProvider
import com.trsolutions.configurations.security.services.KeyService
import io.ktor.util.*
import org.bouncycastle.cms.CMSAlgorithm
import org.bouncycastle.cms.CMSEnvelopedData
import org.bouncycastle.cms.CMSEnvelopedDataGenerator
import org.bouncycastle.cms.CMSProcessableByteArray
import org.bouncycastle.cms.KeyTransRecipientInformation
import org.bouncycastle.cms.jcajce.JceCMSContentEncryptorBuilder
import org.bouncycastle.cms.jcajce.JceKeyTransEnvelopedRecipient
import org.bouncycastle.cms.jcajce.JceKeyTransRecipientInfoGenerator
import java.io.FileInputStream
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import java.util.*


fun String.encrypt(): String {

   val cmsEnvelopedDataGenerator = CMSEnvelopedDataGenerator()
   val jceKey = JceKeyTransRecipientInfoGenerator(retrieveCertificate())

   cmsEnvelopedDataGenerator.addRecipientInfoGenerator(jceKey)

   val message = CMSProcessableByteArray(this.toByteArray())
   val encryptor = JceCMSContentEncryptorBuilder(CMSAlgorithm.AES256_CBC)
      .setProvider("BC")
      .build()

   return cmsEnvelopedDataGenerator
      .generate(message, encryptor)
      .encoded
      .encodeBase64()
}

fun String.decrypt(): String {

   val bytes = Base64
      .getDecoder()
      .decode(this)

   val envelopeData = CMSEnvelopedData(bytes)

   val recipe = envelopeData
      .recipientInfos
      .recipients

   val recipientInfo = recipe
      .iterator()
      .next() as KeyTransRecipientInformation

   val privateKey = JceKeyTransEnvelopedRecipient(KeyService().retrievePrivateKey())

   return String(recipientInfo.getContent(privateKey))
}

private fun retrieveCertificate(): X509Certificate {

   addProvider

   return CertificateFactory
      .getInstance("X.509", "BC")
      .generateCertificate(FileInputStream(readHoconProperty("security.crypto.certs.pem"))) as X509Certificate
}
