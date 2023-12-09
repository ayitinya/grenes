package me.ayitinya.grenes.data.users

import kotlinx.coroutines.runBlocking
import me.ayitinya.grenes.auth.Hashers
import me.ayitinya.grenes.data.Db
import me.ayitinya.grenes.data.location.LocationEntity
import me.ayitinya.grenes.data.location.Locations.city
import me.ayitinya.grenes.data.location.Locations.country
import me.ayitinya.grenes.di.dbModule
import me.ayitinya.grenes.usersList
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.koin.core.context.startKoin
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.test.KoinTest
import org.koin.test.get
import org.koin.test.inject
import kotlin.random.Random

class TestUserDao : KoinTest {

    private lateinit var database: Db
    private val sut: UserDao by inject()

    private var currentTest = Random.nextInt()

    @BeforeEach
    fun setUp() {
        database = get<Db>(named("test")) {
            parametersOf("test${currentTest}")
        }
    }

    @Test
    fun allUsers() {
        runBlocking {
            usersList.forEach { user ->
                transaction {
                    val location = if (user.location != null) LocationEntity.new {
                        city = user.location!!.city
                        country = user.location!!.country
                    } else null
                    UserEntity.new {
                        fullName = user.fullName
                        email = user.email
                        password = Hashers.getHexDigest("password")
                        displayName = user.displayName
                        createdAt = user.createdAt
                        this.location = location
                    }
                }

            }
            val users = sut.allUsers()
            assertEquals(2, users.size)
        }
    }

    @Test
    fun addNewUser() {
        val fullName = "Test User"
        val email = "william.strong@my-own-personal-domain.com"
        val password = "password"
        val displayName = "Test User"

        val location = transaction {
            return@transaction LocationEntity.new {
                city = "Test Location"
                country = "Test Country"
            }
        }


        runBlocking {
            val user = sut.addNewUser(
                fullName = fullName,
                email = email,
                password = password,
                displayName = displayName,
                locationId = location.id.value
            )
            assertEquals(fullName, user.fullName)
            assertEquals(email, user.email)
            assertEquals(displayName, user.displayName)
        }
    }

    @Test
    fun editUser() {
    }

    @Test
    fun deleteUser() {
    }

    @Test
    fun getUser() {
    }

    companion object {
        @JvmStatic
        @BeforeAll
        fun `start koin`(): Unit {
            startKoin {
                modules(dbModule)
            }
        }
    }
}