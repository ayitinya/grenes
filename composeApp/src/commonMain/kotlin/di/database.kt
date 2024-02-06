package di

import app.cash.sqldelight.db.SqlDriver
import me.ayitinya.grenes.Database
import org.koin.core.definition.KoinDefinition
import org.koin.core.module.Module
import org.koin.dsl.module



// this could have just been a function that returns SqlDriver
// don't know why I did it this way
expect fun Module.databaseDriverFactory(): KoinDefinition<SqlDriver>

val databaseModule = module {
    databaseDriverFactory()

    single<Database> {
        val driver: SqlDriver = get()
        Database(driver)
    }
}