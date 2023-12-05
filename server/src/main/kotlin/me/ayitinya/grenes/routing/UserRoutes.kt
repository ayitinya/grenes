package me.ayitinya.grenes.routing

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.request.receive
import io.ktor.server.resources.get
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import me.ayitinya.grenes.data.users.UserDao
import me.ayitinya.grenes.routing.resources.UsersResource
import org.koin.ktor.ext.inject

fun Route.userRoutes() {
    val userDao by inject<UserDao>()

    post<UsersResource.Login> {
        val loginDetails = call.receive<LoginDetails>()

        val user = userDao.authenticateUser(loginDetails.email, loginDetails.password)

        if (user == null) {
            call.respond(HttpStatusCode.NotFound)
        } else {
            call.respond(user)
        }
    }

    get<UsersResource> {
        try {
            val users = userDao.allUsers()
            call.respond(mapOf("users" to users))

        } catch (exception: Exception) {
            println(exception)
            call.respond(HttpStatusCode.InternalServerError)
        }
    }

    authenticate("auth-jwt") {
        get<UsersResource.SessionUserDetails> {
        val principal = call.principal<JWTPrincipal>()
                val username = principal!!.payload.getClaim("email").asString()
                val expiresAt = principal.expiresAt?.time?.minus(System.currentTimeMillis())
                call.respondText("Hello, $username! Token is expired at $expiresAt ms.")
        }
    }
}