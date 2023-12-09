package me.ayitinya.grenes.routing

import com.auth0.jwk.JwkProvider
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.http.content.staticFiles
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.resources.post
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import me.ayitinya.grenes.auth.AuthStates
import me.ayitinya.grenes.auth.authenticateUser
import me.ayitinya.grenes.auth.jwtToken
import me.ayitinya.grenes.data.users.UserDao
import me.ayitinya.grenes.routing.resources.AuthResource
import org.koin.ktor.ext.inject
import java.io.File
import java.util.UUID

@Serializable
data class LoginDetails(val email: String, val password: String)

@Serializable
data class RegistrationDetails(
    val email: String,
    val password: String,
    val displayName: String,
    val fullName: String,
    @Contextual val locationId: UUID? = null
)

internal fun Route.authRoutes(
    privateKeyString: String, issuer: String, audience: String, jwkProvider: JwkProvider
) {

    val userDao: UserDao by inject()

    post<AuthResource.Login> {

        val loginDetails = call.receive<LoginDetails>()

        val user = authenticateUser(
            email = loginDetails.email,
            rawPassword = loginDetails.password
        )

        when (user) {
            is AuthStates.Authenticated -> {
                val jwtToken = jwtToken(
                    email = loginDetails.email,
                    privateKeyString = privateKeyString,
                    issuer = issuer,
                    audience = audience,
                    jwkProvider = jwkProvider
                )
                call.respond(hashMapOf("token" to jwtToken))
            }

            AuthStates.Error -> call.respond(HttpStatusCode.InternalServerError)
            AuthStates.InvalidCredentials -> call.respond(
                HttpStatusCode.Unauthorized, "Invalid credentials"
            )

            AuthStates.UserNotFound -> call.respond(HttpStatusCode.NotFound, "User not found")
        }

    }

    post<AuthResource.Logout> {
        call.respond(HttpStatusCode.OK)
    }

    post<AuthResource.Register> {
        try {
            val userDetails = call.receive<RegistrationDetails>()

            userDao.addNewUser(
                fullName = userDetails.fullName,
                displayName = userDetails.displayName,
                email = userDetails.email,
                password = userDetails.password,
                locationId = userDetails.locationId
            )

            val jwtToken = jwtToken(
                email = userDetails.email,
                privateKeyString = privateKeyString,
                issuer = issuer,
                audience = audience,
                jwkProvider = jwkProvider
            )
            call.respond(hashMapOf("token" to jwtToken))
        } catch (e: Exception) {
            call.respond(HttpStatusCode.InternalServerError, e.message ?: "Error")
        }
    }

}