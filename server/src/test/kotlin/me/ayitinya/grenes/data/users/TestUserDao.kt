package me.ayitinya.grenes.data.users

import kotlinx.coroutines.runBlocking
import me.ayitinya.grenes.auth.Hashers
import me.ayitinya.grenes.data.Db
import me.ayitinya.grenes.data.location.LocationEntity
import me.ayitinya.grenes.di.dbModule
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.test.KoinTest
import org.koin.test.get
import org.koin.test.inject

class TestUserDao : KoinTest {

    private lateinit var database: Db
    private val sut: UserDao by inject()


    @BeforeEach
    fun setUp() {
        database = get<Db>(named("test"))
    }

    @Test
    fun allUsers() {
        runBlocking {
            val users = sut.allUsers()
            assertEquals(1, users.size)
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
            assertTrue(Hashers.verifyPassword(user.password, password))
            assertFalse(Hashers.verifyPassword(user.password, "wrongpassword"))
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