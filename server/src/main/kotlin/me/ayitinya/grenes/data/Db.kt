package me.ayitinya.grenes.data

import kotlinx.coroutines.Dispatchers
import me.ayitinya.grenes.data.location.Locations
import me.ayitinya.grenes.data.media.MediaTable
import me.ayitinya.grenes.data.users.UsersTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

internal class Db(driverClassName: String, jdbcURL: String) {
    val database: Database = Database.connect(jdbcURL, driverClassName)

    init {
        transaction(database) {
            addLogger(StdOutSqlLogger)

            SchemaUtils.create(UsersTable, Locations, MediaTable)
        }
    }

    companion object {
        suspend fun <T> query(block: suspend () -> T): T =
            newSuspendedTransaction(Dispatchers.IO) { block() }
    }
}