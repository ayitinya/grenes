package di

import domain.AuthenticationUseCase
import domain.DefaultAuthenticationUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single<AuthenticationUseCase> {
        DefaultAuthenticationUseCase()
    }
}