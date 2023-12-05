package me.ayitinya.grenes.auth

import com.auth0.jwk.JwkProvider
import com.auth0.jwk.JwkProviderBuilder
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import me.ayitinya.grenes.data.Db
import me.ayitinya.grenes.data.users.User
import me.ayitinya.grenes.data.users.UserEntity
import me.ayitinya.grenes.data.users.UsersTable
import me.ayitinya.grenes.data.users.userEntityToUser
import java.security.KeyFactory
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.util.Base64
import java.util.Date
import java.util.concurrent.TimeUnit

internal suspend fun authenticateUser(email: String, password: String): User? {
    return Db.dbQuery {
        UserEntity.find {
            UsersTable.email eq email
        }.firstOrNull().let {
            if (it == null) {
                return@dbQuery null
            } else {
                if (it.password == Hashers.getHexDigest(password)) {
                    return@dbQuery it.let(::userEntityToUser)
                } else {
                    return@dbQuery null
                }
            }
        }
    }
}

internal fun getJwtToken(
    user: User,
    privateKeyString: String,
    issuer: String,
    audience: String,
    jwkProvider: JwkProvider
): String {
    val publicKey = jwkProvider.get("6f8856ed-9189-488f-9011-0ff4b6c08edc").publicKey
    val keySpecPKCS8 = PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyString))
    val privateKey = KeyFactory.getInstance("RSA").generatePrivate(keySpecPKCS8)

    return JWT.create()
        .withAudience(audience)
        .withIssuer(issuer)
        .withClaim("email", user.email)
        .withExpiresAt(Date(System.currentTimeMillis() + 60000))
        .sign(Algorithm.RSA256(publicKey as RSAPublicKey, privateKey as RSAPrivateKey))
}