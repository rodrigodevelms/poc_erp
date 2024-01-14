package com.trsolutions.configurations.security.provider

import org.bouncycastle.jce.provider.BouncyCastleProvider
import java.security.Security


object BouncyCastleProvider {

   val addProvider = Security.addProvider(BouncyCastleProvider())
}
