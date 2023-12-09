package me.ayitinya.grenes.data.users

import kotlinx.datetime.Clock
import me.ayitinya.grenes.auth.Hashers.getHexDigest
import me.ayitinya.grenes.data.Db
import me.ayitinya.grenes.data.location.Location
import me.ayitinya.grenes.data.location.LocationEntity
import org.jetbrains.exposed.sql.update
import java.util.UUID


class DefaultUserDao : UserDao {
    private fun UserEntity.toUser(): User {
        try {
            return User(
                id = id.toString(),
                fullName = fullName,
                displayName = displayName,
                email = email,
                createdAt = createdAt,
                location = if (location != null) Location(
                    id = location!!.id.toString(),
                    city = location!!.city,
                    country = location!!.country
                ) else null,
            )

        } catch (error: Exception) {
            throw error
        }

    }


    private fun List<UserEntity>.toUsers(): List<User> = this.map { it.toUser() }

    override suspend fun allUsers(): List<User> = Db.query {
        UserEntity.all().toList().toUsers()
    }

    override suspend fun addNewUser(
        fullName: String, displayName: String, email: String, password: String, locationId: UUID?
    ): User = Db.query {
        val location = locationId?.let {
            LocationEntity.findById(it)
        }


            UserEntity.new {
                this.fullName = fullName
                this.displayName = displayName
                this.email = email
                this.password = getHexDigest(password)
                this.createdAt = Clock.System.now()
                this.location = location
            }
        .toUser()

    }

    override suspend fun editUser(userEntity: UserEntity) = Db.query {
        UsersTable.update({ UsersTable.id eq userEntity.id }) {
            it[this.fullName] = userEntity.fullName
            it[this.displayName] = userEntity.displayName
            it[this.email] = userEntity.email
            it[this.createdAt] = userEntity.createdAt
        } > 0
    }

    override suspend fun deleteUser(userId: UUID): Boolean = Db.query {
        val userEntity = UserEntity.findById(userId)

        if (userEntity != null) {
            userEntity.delete()
            return@query true
        } else {
            return@query false
        }
    }

    override suspend fun getUserByEmail(email: String): User? = Db.query {
        return@query UserEntity.find { UsersTable.email eq email }.firstOrNull()?.toUser()
    }

    override suspend fun getUserById(userId: UUID): User? = Db.query {
        UserEntity.findById(userId)?.toUser()
    }
}