package di

import data.app.AppPreferences
import data.app.DefaultAppPreferences
import data.auth.AuthRepository
import data.auth.DefaultAuthRepository
import data.challenges.ChallengesRepository
import data.challenges.DefaultChallengesRepository
import data.challenges.remote.ChallengeNetworkDataSource
import data.challenges.remote.DefaultChallengeNetworkDataSource
import data.dataStorePreferences
import data.users.DefaultUsersRepository
import data.users.UsersRepository
import data.users.remote.DefaultUserNetworkDataSource
import data.users.remote.UserNetworkDataSource
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import org.koin.dsl.module

val appModule = module {
    single<FirebaseAuth> {
        val auth = Firebase.auth
        auth.useEmulator("10.0.2.2", 9099)

        return@single auth
    }

    single<UserNetworkDataSource> {
        DefaultUserNetworkDataSource(httpClient = get())
    }

    single<UsersRepository> {
        DefaultUsersRepository(userNetworkDataSource = get())
    }

    single<ChallengeNetworkDataSource> {
        DefaultChallengeNetworkDataSource(httpClient = get())
    }

    single<AppPreferences> {
        DefaultAppPreferences(dataStore = get())
    }

    single<AuthRepository> {
        DefaultAuthRepository(auth = get())
    }

    single(createdAtStart = true) {
        dataStorePreferences(
            corruptionHandler = null,
            migrations = emptyList(),
            coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
        )
    }

    single<ChallengesRepository> {
        DefaultChallengesRepository(challengeNetworkDataSource = get())
    }
}