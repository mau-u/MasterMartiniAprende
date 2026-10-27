package com.example.mastermartiniaprende.security

import android.util.Base64
import java.security.MessageDigest
import java.security.SecureRandom
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

object PasswordHasher {
    private const val ITERATIONS = 120_000
    private const val KEY_LENGTH_BYTES = 32
    private const val HMAC_ALGORITHM = "HmacSHA256"

    fun crearSalt(): String {
        val bytes = ByteArray(16)
        SecureRandom().nextBytes(bytes)
        return Base64.encodeToString(bytes, Base64.NO_WRAP)
    }

    fun generarHash(password: String, salt: String): String {
        val passwordBytes = password.toByteArray(Charsets.UTF_8)
        val saltBytes = Base64.decode(salt, Base64.NO_WRAP)
        val derivedKey = try {
            pbkdf2Sha256(passwordBytes, saltBytes)
        } finally {
            passwordBytes.fill(0)
        }

        return try {
            Base64.encodeToString(derivedKey, Base64.NO_WRAP)
        } finally {
            derivedKey.fill(0)
        }
    }

    fun verificar(password: String, salt: String, hashGuardado: String): Boolean {
        val hashCalculado = Base64.decode(generarHash(password, salt), Base64.NO_WRAP)
        val hashEsperado = Base64.decode(hashGuardado, Base64.NO_WRAP)
        return MessageDigest.isEqual(hashCalculado, hashEsperado)
    }

    private fun pbkdf2Sha256(password: ByteArray, salt: ByteArray): ByteArray {
        val mac = Mac.getInstance(HMAC_ALGORITHM)
        mac.init(SecretKeySpec(password, HMAC_ALGORITHM))

        val output = ByteArray(KEY_LENGTH_BYTES)
        val blockCount = (KEY_LENGTH_BYTES + mac.macLength - 1) / mac.macLength
        var outputOffset = 0

        for (blockIndex in 1..blockCount) {
            val saltWithBlockIndex = ByteArray(salt.size + 4)
            System.arraycopy(salt, 0, saltWithBlockIndex, 0, salt.size)
            saltWithBlockIndex[salt.size] = (blockIndex ushr 24).toByte()
            saltWithBlockIndex[salt.size + 1] = (blockIndex ushr 16).toByte()
            saltWithBlockIndex[salt.size + 2] = (blockIndex ushr 8).toByte()
            saltWithBlockIndex[salt.size + 3] = blockIndex.toByte()

            var u = mac.doFinal(saltWithBlockIndex)
            val accumulator = u.copyOf()

            for (round in 1 until ITERATIONS) {
                u = mac.doFinal(u)
                for (index in accumulator.indices) {
                    accumulator[index] =
                        (accumulator[index].toInt() xor u[index].toInt()).toByte()
                }
            }

            val bytesToCopy = minOf(accumulator.size, KEY_LENGTH_BYTES - outputOffset)
            System.arraycopy(accumulator, 0, output, outputOffset, bytesToCopy)
            outputOffset += bytesToCopy

            saltWithBlockIndex.fill(0)
            u.fill(0)
            accumulator.fill(0)
        }

        return output
    }
}