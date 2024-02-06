package me.ayitinya.grenes

import io.ktor.server.application.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.autohead.*
import io.ktor.server.resources.*
import io.ktor.server.routing.*
import me.ayitinya.grenes.config.firebase.FirebaseAdmin
import me.ayitinya.grenes.data.Db
import me.ayitinya.grenes.di.dbModule
import me.ayitinya.grenes.plugins.configureAuthentication
import me.ayitinya.grenes.plugins.configureRequestValidation
import me.ayitinya.grenes.plugins.configureRouting
import me.ayitinya.grenes.plugins.configureSerialization
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.koin.ktor.ext.get
import org.koin.ktor.ext.inject
import org.koin.ktor.plugin.Koin
import org.koin.ktor.plugin.KoinApplicationStarted
import org.koin.ktor.plugin.KoinApplicationStopPreparing
import org.koin.ktor.plugin.KoinApplicationStopped
import org.koin.logger.slf4jLogger

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.di() {

    install(Koin) {
        slf4jLogger()
        modules(dbModule)
    }

    environment.monitor.subscribe(KoinApplicationStarted) {
        log.info("Koin started.")
    }

    environment.monitor.subscribe(KoinApplicationStopPreparing) {
        log.info("Koin stopping...")
    }

    environment.monitor.subscribe(KoinApplicationStopped) {
        log.info("Koin stopped.")
    }
}

fun Application.configureDb() {
    val db: Db by inject()
    TransactionManager.defaultDatabase = db.database
}


fun Application.main(testing: Boolean = false) {
    install(Resources) // install before routing

    if (!testing) {
        di()
        configureDb()
    }

    configureAuthentication() // install before routing

    configureRouting()
    configureSerialization()

    configureRequestValidation()

    install(IgnoreTrailingSlash)
    install(AutoHeadResponse)
}
