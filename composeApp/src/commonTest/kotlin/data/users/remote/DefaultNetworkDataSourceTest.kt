package data.users.remote

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.auth
import di.appModule
import di.httpClientModule
import io.ktor.client.*
import org.koin.core.component.get
import org.koin.core.context.startKoin
import org.koin.test.KoinTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class DefaultNetworkDataSourceTest : KoinTest {
    private lateinit var sut: NetworkDataSource


    @Test
    fun `test user create with email and password`() {
        startKoin {
            modules(httpClientModule, appModule)
        }

        val auth = Firebase.auth
        auth.useEmulator("10.0.2.2", 9099)

        sut = DefaultNetworkDataSource(httpClient = get(), auth = auth)


    }
}