package di

import org.koin.dsl.module
import ui.screens.home.HomeViewModel
import ui.screens.register.RegisterViewModel

val viewModelModule = module {
    factory {
        HomeViewModel()
    }

    factory {
        RegisterViewModel()
    }
}