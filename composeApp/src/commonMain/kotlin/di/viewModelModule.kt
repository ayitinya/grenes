package di

import navigation.SharedViewModel
import org.koin.dsl.module
import ui.screens.home.HomeViewModel
import ui.screens.onboarding.authentication.AuthScreenViewModel
import ui.screens.onboarding.profile.ProfileScreenViewModel

val viewModelModule = module {
    factory {
        HomeViewModel()
    }

    factory {
        ProfileScreenViewModel(get())
    }

    factory {
        AuthScreenViewModel(get(), get())
    }

    factory {
        SharedViewModel(get())
    }
}