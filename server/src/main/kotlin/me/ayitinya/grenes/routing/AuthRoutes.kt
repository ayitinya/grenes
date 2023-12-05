package me.ayitinya.grenes.routing

import com.auth0.jwk.JwkProvider
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import kotlinx.serialization.Serializable
import me.ayitinya.grenes.auth.authenticateUser
import me.ayitinya.grenes.auth.getJwtToken
import me.ayitinya.grenes.routing.resources.AuthResource

@Serializable
data class LoginDetails(val email: String, val password: String)

internal fun Route.authRoutes(
    privateKeyString: String, issuer: String, audience: String, jwkProvider: JwkProvider
) {

    post<AuthResource.Login> {

        val loginDetails = call.receive<LoginDetails>()

        val user = authenticateUser(email = loginDetails.email, password = loginDetails.password)

        if (user != null) {
            call.respond(
                hashMapOf(
                    "token" to getJwtToken(
                        user, privateKeyString, issuer, audience, jwkProvider
                    )
                )
            )
        }

    }
}