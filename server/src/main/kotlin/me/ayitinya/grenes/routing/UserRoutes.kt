package me.ayitinya.grenes.routing

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserRecord.UpdateRequest
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.resources.post
import io.ktor.server.response.*
import io.ktor.server.routing.Route
import me.ayitinya.grenes.auth.firebase.FIREBASE_AUTH
import me.ayitinya.grenes.auth.firebase.FirebaseUserPrincipal
import me.ayitinya.grenes.data.users.User
import me.ayitinya.grenes.data.users.UserDao
import me.ayitinya.grenes.routing.resources.UsersResource
import org.koin.ktor.ext.inject

fun Route.userRoutes() {
    val userDao by inject<UserDao>()

    get<UsersResource> {
        try {
            val users = userDao.allUsers()
            call.respond(mapOf("users" to users))

        } catch (exception: Exception) {
            println(exception)
            call.respond(HttpStatusCode.InternalServerError)
        }
    }

    authenticate("auth-jwt", FIREBASE_AUTH) {
        get<UsersResource.SessionUserDetails> {
            this.context.authentication.principal<JWTPrincipal>()?.let { principal ->
                val username = principal.payload.getClaim("email").asString()
                val expiresAt = principal.expiresAt?.time?.minus(System.currentTimeMillis())
                call.respondText("Hello, $username! Token is expired at $expiresAt ms.")
                return@get
            }

//            this.context.authentication.principal<UserPrincipal>()?.let { principal ->
//                call.respond(mapOf("user" to principal))
//            }
        }
    }

    authenticate(FIREBASE_AUTH) {
        post<UsersResource.Register> {
            this.context.authentication.principal<FirebaseUserPrincipal>()?.let { principal ->
                println("User: $principal")
            }
        }
    }

    authenticate(FIREBASE_AUTH) {
        post<UsersResource.CreateUserWithUid> {
            this.context.authentication.principal<FirebaseUserPrincipal>()?.let { principal ->
                val user = FirebaseAuth.getInstance().getUser(principal.uid)

                user.email?.let { email ->
                    userDao.createNewUserWithUidAndEmail(principal.uid, email)
                }
            }
        }
    }

    authenticate(FIREBASE_AUTH) {
        post<UsersResource.SessionUserDetails> {
            this.context.authentication.principal<FirebaseUserPrincipal>()?.let { principal ->
                try {
                    val user = call.receive<User>()
                    userDao.updateUser(principal.uid, user)

                    val updateRequest = UpdateRequest(principal.uid).setDisplayName(user.displayName)
                        .setPhotoUrl(user.profileAvatar)
                    FirebaseAuth.getInstance().updateUser(updateRequest)
                } catch (exception: Exception) {
                    println(exception)
                    exception.printStackTrace()
                    call.respond(HttpStatusCode.InternalServerError)
                }
            }
        }
    }
}