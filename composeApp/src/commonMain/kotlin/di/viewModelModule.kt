package di

import org.koin.dsl.module
import ui.home.HomeViewModel

val viewModelModule = module {
    factory {
        HomeViewModel()
    }
}