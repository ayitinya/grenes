package di

import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.plugins.resources.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val httpClientModule = module {
    single {
        HttpClient {
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }

            install(DefaultRequest) {
                header("Content-Type", "application/json")
                url {
                    protocol = URLProtocol.HTTP
                    port = 8080
                    host = "10.0.2.2"
                }
            }

//            install(HttpRequestRetry) {
//                retryOnServerErrors(maxRetries = 5)
//                exponentialDelay()
//            }

            install(Auth) {
                bearer { }
            }

            install(Resources)

            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = false
                })
            }

        }
    }
}