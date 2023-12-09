package me.ayitinya.grenes.plugins

import Greeting
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import me.ayitinya.grenes.routing.userRoutes

fun Application.configureRouting() {
    routing {

        staticResources("/.well-known", "assets")

        userRoutes()

        get("/") {
            call.respondText("Ktor: ${Greeting().greet()}")
        }
    }
}