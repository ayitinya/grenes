package di

import org.koin.core.module.Module

val commonModules = listOf(viewModelModule, useCaseModule, appModule, httpClientModule, databaseModule)

expect val platformModules: List<Module>
