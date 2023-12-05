package me.ayitinya.grenes.data

import kotlinx.coroutines.Dispatchers
import me.ayitinya.grenes.data.location.Locations
import me.ayitinya.grenes.data.users.UsersTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

internal class Db(driverClassName: String, jdbcURL: String) {
    private var database: Database

    init {
        database = Database.connect(jdbcURL, driverClassName)

        transaction(database) {
            addLogger(StdOutSqlLogger)

            SchemaUtils.create(UsersTable, Locations)
        }
    }

    companion object {
        suspend fun <T> dbQuery(block: suspend () -> T): T =
            newSuspendedTransaction(Dispatchers.IO) { block() }
    }

}