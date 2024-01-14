package com.trsolutions.configurations.security.extensions

import de.mkammerer.argon2.Argon2Factory


private const val SALT_LENGTH: Int = 32
private const val HASH_LENGTH: Int = 64
private const val ITERATIONS: Int = 3
private const val MEMORY: Int = 64 * 1024
private const val PARALLELISM: Int = 1

private val argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id, SALT_LENGTH, HASH_LENGTH)
fun String.encode(): String = argon2.hash(ITERATIONS, MEMORY, PARALLELISM, this.toCharArray())
fun String.checkEncoding(hash: String): Boolean = argon2.verify(hash, this.toCharArray())
