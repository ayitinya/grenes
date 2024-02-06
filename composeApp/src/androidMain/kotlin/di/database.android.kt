package di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import me.ayitinya.grenes.Database
import org.koin.core.definition.KoinDefinition
import org.koin.core.module.Module

actual fun Module.databaseDriverFactory(): KoinDefinition<SqlDriver> = single {
    AndroidSqliteDriver(Database.Schema, get(), "test.db")
}