package me.ayitinya.grenes.data.users

import kotlinx.coroutines.runBlocking
import me.ayitinya.grenes.auth.Hashers
import me.ayitinya.grenes.data.location.Location
import me.ayitinya.grenes.data.location.Locations
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class TestUserDao {

    private lateinit var database: Database
    private val sut: UserDao = DefaultUserDao


    @BeforeEach
    fun setUp() {
        database = Database.connect("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", driver = "org.h2.Driver")

        transaction(database) {
            addLogger(StdOutSqlLogger)
            SchemaUtils.create(Users, Locations)
        }
    }

    @AfterEach
    fun tearDown() {
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
            return@transaction Location.new {
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
}