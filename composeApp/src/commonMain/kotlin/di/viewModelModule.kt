package di

import org.koin.dsl.module
import ui.screens.home.HomeViewModel
import ui.screens.onboarding.authentication.AuthScreenViewModel
import ui.screens.onboarding.profile.ProfileScreenViewModel

val viewModelModule = module {
    factory {
        HomeViewModel()
    }

    factory {
        ProfileScreenViewModel()
    }

    factory {
        AuthScreenViewModel(get())
    }
}