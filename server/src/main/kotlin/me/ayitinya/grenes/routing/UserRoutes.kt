package me.ayitinya.grenes.routing

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.resources.get
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import kotlinx.serialization.Serializable
import me.ayitinya.grenes.auth.Hashers
import me.ayitinya.grenes.data.media.Medias.user
import me.ayitinya.grenes.data.users.DefaultUserDao
import me.ayitinya.grenes.routing.resources.UsersResource

@Serializable
data class LoginDetails(val email: String, val password: String)

fun Route.userRoutes() {
    post<UsersResource.Login> {
        val loginDetails = call.receive<LoginDetails>()

//        val user = DefaultUserDao.getUserByEmail(loginDetails.email)

        val user = DefaultUserDao.authenticateUser(loginDetails.email, loginDetails.password)

        if (user == null) {
            call.respond(HttpStatusCode.NotFound)
        } else {
            call.respond(user)
        }
    }

    get<UsersResource> {
        try {
            val users = DefaultUserDao.allUsers()
            call.respond(mapOf("users" to users))

        } catch (exception: Exception) {
            println(exception)
            call.respond(HttpStatusCode.InternalServerError)
        }
    }


}