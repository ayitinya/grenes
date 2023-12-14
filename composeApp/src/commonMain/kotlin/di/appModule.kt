package di

import data.users.DefaultUsersRepository
import data.users.UsersRepository
import data.users.remote.DefaultNetworkDataSource
import data.users.remote.NetworkDataSource
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import org.koin.dsl.module

val appModule = module {
    single<NetworkDataSource> {
        val auth = Firebase.auth
        auth.useEmulator("10.0.2.2", 9099)

        DefaultNetworkDataSource(httpClient = get(), auth = auth)
    }

    single<UsersRepository> {
        DefaultUsersRepository(networkDataSource = get())
    }
}