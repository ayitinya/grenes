package me.ayitinya.grenes

import Greeting
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.netty.EngineMain
import io.ktor.server.plugins.autohead.AutoHeadResponse
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.resources.Resources
import io.ktor.server.response.respondText
import io.ktor.server.routing.IgnoreTrailingSlash
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import me.ayitinya.grenes.data.Database
import me.ayitinya.grenes.routing.userRoutes

//fun main() {
//    embeddedServer(Netty, port = SERVER_PORT, host = "0.0.0.0", module = Application::module)
//        .start(wait = true)
//}

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {
    install(Authentication) {
        jwt {}
    }
    install(IgnoreTrailingSlash)
    install(Resources)
    install(AutoHeadResponse)
    install(ContentNegotiation) {
        json()
    }
    Database.init()

    routing {
        userRoutes()
        get("/") {
            call.respondText("Ktor: ${Greeting().greet()}")
        }
    }
}
