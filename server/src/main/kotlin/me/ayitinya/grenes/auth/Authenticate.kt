package me.ayitinya.grenes.auth

import com.auth0.jwk.JwkProvider
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import me.ayitinya.grenes.data.Db
import me.ayitinya.grenes.data.users.UserEntity
import me.ayitinya.grenes.data.users.UsersTable
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.security.KeyFactory
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.util.Base64
import java.util.Date


internal suspend fun authenticateUser(
    email: String,
    rawPassword: String
): AuthStates {

    val user = newSuspendedTransaction {
        return@newSuspendedTransaction Db.query { UserEntity.find { UsersTable.email eq email } }
            .firstOrNull()
    }

    return try {
        if (user != null) {
            if (Hashers.verifyPassword(user.password, rawPassword)) {
                return AuthStates.Authenticated

            } else {
                AuthStates.InvalidCredentials
            }
        } else {
            AuthStates.UserNotFound
        }
    } catch (e: Exception) {
        return AuthStates.Error
    }
}

internal fun jwtToken(
    email: String,
    privateKeyString: String,
    issuer: String,
    audience: String,
    jwkProvider: JwkProvider
): String {

    try {
        val publicKey = jwkProvider.get("6f8856ed-9189-488f-9011-0ff4b6c08edc").publicKey
        val keySpecPKCS8 = PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyString))
        val privateKey = KeyFactory.getInstance("RSA").generatePrivate(keySpecPKCS8)

        return JWT.create().withAudience(audience).withIssuer(issuer).withClaim("email", email)
            .withExpiresAt(Date(System.currentTimeMillis() + 60000))
            .sign(Algorithm.RSA256(publicKey as RSAPublicKey, privateKey as RSAPrivateKey))

    } catch (error: Exception) {
        println(error.message)
        throw error
    }
}
