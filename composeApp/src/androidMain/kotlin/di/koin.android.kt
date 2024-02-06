package di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import me.ayitinya.grenes.Database
import data.dataStorePreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.module.Module
import org.koin.dsl.module

//val appModules = module {
//    single(createdAtStart = true) {
//        dataStorePreferences(
//            corruptionHandler = null,
//            migrations = emptyList(),
//            coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
//        )
//    }
//}


actual val platformModules: List<Module> = listOf()