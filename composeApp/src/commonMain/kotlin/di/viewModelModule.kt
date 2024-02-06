package di

import navigation.SharedViewModel
import org.koin.dsl.module
import ui.screens.challenges.ChallengesViewModel
import ui.screens.home.HomeViewModel
import ui.screens.onboarding.authentication.AuthScreenViewModel
import ui.screens.onboarding.profile.OnboardingProfileScreenViewModel
import ui.screens.user.ProfileScreenViewModel

val viewModelModule = module {
    factory {
        HomeViewModel()
    }

    factory {
        OnboardingProfileScreenViewModel(usersRepository = get(), authRepository = get(), appPreferences = get())
    }

    factory {
        AuthScreenViewModel(get(), get())
    }

    single(createdAtStart = true) {
        SharedViewModel(authenticationUseCase = get(), appPreferences = get())
    }

    factory { params ->
        ProfileScreenViewModel(uid = params.get(), usersRepository = get(), authenticationUseCase = get())
    }

    factory {
        ChallengesViewModel(challengesRepository = get())
    }
}