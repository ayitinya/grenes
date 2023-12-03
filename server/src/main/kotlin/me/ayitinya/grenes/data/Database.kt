package me.ayitinya.grenes.data

import kotlinx.coroutines.Dispatchers
import me.ayitinya.grenes.data.location.Locations
import me.ayitinya.grenes.data.users.Users
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object Database {
    private lateinit var database: Database

    fun init() {
        val driverClassName = "org.h2.Driver"
        val jdbcURL = "jdbc:h2:file:./build/db"
        database = Database.connect(jdbcURL, driverClassName)

        transaction(database) {
            addLogger(StdOutSqlLogger)

            SchemaUtils.create(Users, Locations)
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}