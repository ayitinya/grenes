package me.ayitinya.grenes.plugins

import Greeting
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import me.ayitinya.grenes.routing.challengeRoutes
import me.ayitinya.grenes.routing.userRoutes

fun Application.configureRouting() {
    routing {

        staticResources("/.well-known", "assets")

        userRoutes()
        challengeRoutes()

        get("/") {
            call.respondText("Ktor: ${Greeting().greet()}")
        }
    }

    install(CORS) {
        allowHeader(HttpHeaders.ContentType)
        allowMethod(HttpMethod.Get)
        allowMethod(HttpMethod.Post)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        anyHost()
    }
}